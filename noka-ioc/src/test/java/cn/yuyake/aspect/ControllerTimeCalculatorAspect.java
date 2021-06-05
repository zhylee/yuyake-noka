package cn.yuyake.aspect;

import cn.yuyake.aop.annotation.Aspect;
import cn.yuyake.aop.annotation.Order;
import cn.yuyake.aop.aspect.DefaultAspect;
import cn.yuyake.core.annotation.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@Aspect(pointcut = "execution(* cn.yuyake.reflect..*.*(..))")
@Order(0)
public class ControllerTimeCalculatorAspect extends DefaultAspect {

    private static final Logger logger = LogManager.getLogger(ControllerTimeCalculatorAspect.class);

    private long timestampCache;

    @Override
    public void before(Class<?> targetClass, Method method, Object[] args) throws Throwable {
        logger.info("开始计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]", targetClass.getName(), method.getName(), args);
        timestampCache = System.currentTimeMillis();
    }

    @Override
    public Object afterReturning(Class<?> targetClass, Method method, Object[] args, Object returnValue) throws Throwable {
        long endTime = System.currentTimeMillis();
        long costTime = endTime - timestampCache;
        logger.info("结束计时，执行的类是[{}]，执行的方法是[{}]，参数是[{}]，返回值是[{}]，时间为[{}]ms",
                targetClass.getName(), method.getName(), args, returnValue, costTime);
        return returnValue;
    }
}
