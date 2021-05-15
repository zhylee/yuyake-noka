package cn.yuyake.cglib;

public class CglibDemo {
    public static void main(String[] args) {
        var commonPayment = new CommonPayment();
        var methodInterceptor = new AlipayMethodInterceptor();
        var commonPaymentProxy = CglibUtil.createProxy(commonPayment, methodInterceptor);
        commonPaymentProxy.pay();
    }
}
