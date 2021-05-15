package cn.yuyake.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AlipayMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        beforePay();
        Object result = proxy.invokeSuper(obj, args);
        afterPay();
        return result;
    }

    private void beforePay() {
        System.out.println("从银行取款");
    }

    private void afterPay() {
        System.out.println("支付给其他人");
    }
}
