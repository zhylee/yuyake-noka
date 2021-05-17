package cn.yuyake.aspect;

import cn.yuyake.aop.annotation.Aspect;
import cn.yuyake.aop.annotation.Order;
import cn.yuyake.aop.aspect.DefaultAspect;
import cn.yuyake.core.annotation.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@Aspect(value = Controller.class)
@Order(10)
public class ControllerInfoRecordAspect extends DefaultAspect {

    private static final Logger logger = LogManager.getLogger(ControllerInfoRecordAspect.class);

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        logger.info("开始计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]",
                targetClass.getName(), method.getName(), args);
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        logger.info("结束计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]，返回值是[{}]",
                targetClass.getName(), method.getName(), args, returnValue);
        return returnValue;
    }

    @Override
    public void afterThrowing(Class<?> targetClass, Method method, Object[] args, Throwable e) throws Throwable {
        logger.info("方法执行失败，执行的类是[{}]，执行的方法是[{}]，参数是[{}]，异常是[{}]",
                targetClass.getName(), method.getName(), args, e.getMessage());
    }
}
