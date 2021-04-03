package cn.yuyake.relfect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 获取成员方法并调用
 * <p>    1. 批量的：
 * <p>        public Method[] getMethods(); // 获取所有公有方法，包含了父类的方法也包含Object类
 * <p>        public Method[] getDeclaredMethods(); // 获取所有的成员方法，包括私有但不包括继承的
 * <p>    2. 获取单个的：
 * <p>        public Method getMethod(String name, Class... parameterTypes);
 * <p>            参数说明：name：方法名；Class...：形参的Class类型对象
 * <p>        public Method getDeclaredMethod(String name, Class... parameterTypes);
 * <p>    3. 调用方法：
 * <p>        Method --> public Object invoke(Object obj, Object... args);
 * <p>            参数说明：obj：要调用方法的对象；args：调用方法时所传递的实参
 * <p>
 * create by yeah on 2021/4/3 11:34
 */
public class MethodCollector {

    public static void main(String[] args)
        throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 1. 获取Class对象
        var reflectTargetClass = Class.forName("cn.yuyake.relfect.ReflectTarget");
        // 2. 获取所有公有方法
        System.out.println("*************获取所有公有方法，包括父类和Object*************");
        var methodArray = reflectTargetClass.getMethods();
        for (Method m : methodArray) {
            System.out.println(m);
        }
        // 3. 获取该类的所有方法
        System.out.println("*************获取所有方法，包括私有的*************");
        methodArray = reflectTargetClass.getDeclaredMethods();
        for (Method m : methodArray) {
            System.out.println(m);
        }
        // 4. 获取单个公有方法
        System.out.println("*************获取公有的方法 show1*************");
        var m = reflectTargetClass.getMethod("show1", String.class);
        System.out.println(m);
        // 5. 调用show1并执行
        var reflectTarget = (ReflectTarget) reflectTargetClass.getConstructor().newInstance();
        m.invoke(reflectTarget, "待反射方法一号");
        // 6. 获取一个私有的成员方法
        System.out.println("*************获取私有的方法 show4*************");
        m = reflectTargetClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);
        var result = String.valueOf(m.invoke(reflectTarget, 20));
    }
}
