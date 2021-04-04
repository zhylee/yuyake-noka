package cn.yuyake.relfect;

import cn.yuyake.core.annotation.Controller;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取构造方法：
 * <p>    1. 批量的方法
 * <p>        public Constructor[] getConstructors(); 所有“公有的”构造方法
 * <p>        public Constructor[] getDeclaredConstructors(); 所有的构造方法（包括私有、受保护、默认、公有）
 * <p>    2. 获取单个方法
 * <p>        public Constructor getConstructor(Class... parameterTypes); 获取单个“公有的”构造方法
 * <p>        public Constructor getDeclaredConstructor(Class... parameterTypes); 获取某个构造方法
 * <p>    3. 调用构造方法
 * <p>        Constructor --> newInstance(Object... initargs);
 * <p>
 * create by yeah on 2021/4/3 10:28
 */
@Controller
public class ConstructorCollector {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {
        var clazz = Class.forName("cn.yuyake.relfect.ReflectTarget");
        // 1. 获取所有公有构造方法
        System.out.println("*************所有公有构造方法*************");
        var conArray = clazz.getConstructors();
        for (Constructor<?> c : conArray) {
            System.out.println(c);
        }
        // 2. 获取所有的构造方法
        System.out.println("*************所有构造方法（包括私有、受保护、默认、公有）*************");
        conArray = clazz.getDeclaredConstructors();
        for (Constructor<?> c : conArray) {
            System.out.println(c);
        }
        // 3. 获取单个带参数的公有方法
        System.out.println("*************获取公有且带两个参数的构造方法*************");
        var con = clazz.getConstructor(String.class, int.class);
        System.out.println("con = " + con);
        // 4. 获取单个私有的构造方法
        System.out.println("*************获取私有构造方法*************");
        con = clazz.getDeclaredConstructor(int.class);
        System.out.println("private con = " + con);
        // 5. 调用私有构造方法创建实例
        System.out.println("*************调用私有构造方法创建实例*************");
        con.setAccessible(true); // 暴力访问（忽略掉访问修饰符）
        var reflectTarget = (ReflectTarget) con.newInstance(1);

    }

}
