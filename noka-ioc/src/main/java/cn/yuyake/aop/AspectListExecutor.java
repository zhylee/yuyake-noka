package cn.yuyake.aop;

import cn.yuyake.aop.aspect.AspectInfo;
import cn.yuyake.util.ValidationUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

public class AspectListExecutor implements MethodInterceptor {
    // 被代理的类
    private final Class<?> targetClass;
    // 排序好的 Aspect 列表
    private final List<AspectInfo> sortedAspectInfoList;

    public AspectListExecutor(Class<?> targetClass, List<AspectInfo> aspectInfoList) {
        this.targetClass = targetClass;
        this.sortedAspectInfoList = sortAspectInfoList(aspectInfoList);
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public List<AspectInfo> getSortedAspectInfoList() {
        return sortedAspectInfoList;
    }

    /**
     * 按照 order 的值进行升序排序，确保 order 值小的 aspect 先被织入
     *
     * @param aspectInfoList
     * @return
     */
    private List<AspectInfo> sortAspectInfoList(List<AspectInfo> aspectInfoList) {
        // 按照值的大小进行升序排序
        aspectInfoList.sort(Comparator.comparingInt(AspectInfo::getOrderIndex));
        return aspectInfoList;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object returnValue = null;
        collectAccurateMatchedAspectList(method);
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            returnValue = proxy.invokeSuper(obj, args);
            return returnValue;
        }
        // 1. 按照 order 的顺序升序执行完所有 Aspect 的 before 方法
        invokeBeforeAdvices(method, args);
        try {
            // 2. 执行被代理类的方法
            returnValue = proxy.invokeSuper(obj, args);
            // 3. 如果被代理方法正常返回，则按照 order 的顺序降序执行完所有 Aspect 的 afterReturning 方法
            returnValue = invokeAfterReturningAdvices(method, args, returnValue);
        } catch (Exception e) {
            // 4. 如果被代理方法正常返回，则按照 order 的顺序降序执行完所有 Aspect 的 afterThrowing 方法
            invokeAfterThrowingAdvices(method, args, e);
        }


        return returnValue;
    }

    private void collectAccurateMatchedAspectList(Method method) {
        if (ValidationUtil.isEmpty(sortedAspectInfoList)) {
            return;
        }
        sortedAspectInfoList.removeIf(aspectInfo -> !aspectInfo.getPointcutLocator().accurateMatches(method));
    }

    // 1. 按照 order 的顺序升序执行完所有 Aspect 的 before 方法
    private void invokeBeforeAdvices(Method method, Object[] args) throws Throwable {
        for (AspectInfo aspectInfo : sortedAspectInfoList) {
            aspectInfo.getAspectObject().before(targetClass, method, args);
        }
    }

    // 3. 如果被代理方法正常返回，则按照 order 的顺序降序执行完所有 Aspect 的 afterReturning 方法
    private Object invokeAfterReturningAdvices(Method method, Object[] args, Object returnValue) throws Throwable {
        Object result = null;
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            result = sortedAspectInfoList.get(i).getAspectObject().afterReturning(targetClass, method, args, returnValue);
        }
        return result;
    }

    // 4. 如果被代理方法正常返回，则按照 order 的顺序降序执行完所有 Aspect 的 afterThrowing 方法
    private void invokeAfterThrowingAdvices(Method method, Object[] args, Exception e) throws Throwable {
        for (int i = sortedAspectInfoList.size() - 1; i >= 0; i--) {
            sortedAspectInfoList.get(i).getAspectObject().afterThrowing(targetClass, method, args, e);
        }
    }
}
