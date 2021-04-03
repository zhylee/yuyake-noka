package cn.yuyake.singletion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * create by yeah on 2021/4/3 17:37
 */
public class SingletonDemo {

    public static void main(String[] args)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println(StarvingSingleton.getInstance());
        Class clazz1 = StarvingSingleton.class;
        Constructor constructor1 = clazz1.getDeclaredConstructor();
        constructor1.setAccessible(true);
        System.out.println(constructor1.newInstance());

        System.out.println(LazyDoubleCheckSingleton.getInstance());
        Class clazz2 = LazyDoubleCheckSingleton.class;
        Constructor constructor2 = clazz2.getDeclaredConstructor();
        constructor2.setAccessible(true);
        System.out.println(constructor2.newInstance());
        // 为啥这样改结果都是一样啊啊啊啊啊啊啊啊啊，理由见下下面...
        // LazyDoubleCheckSingleton lazyDoubleCheckSingleton = (LazyDoubleCheckSingleton) constructor2.newInstance();
        // System.out.println(lazyDoubleCheckSingleton.getInstance());

        System.out.println(EnumStarvingSingleton.getInstance());
        Class clazz3 = EnumStarvingSingleton.class;
        Constructor constructor3 = clazz3.getDeclaredConstructor();
        constructor3.setAccessible(true);
        // 这里为啥和之前的不一样？因为要获取的是 getInstance 里面的实例，而不是 new 出来的对象本身
        // 为什么前面按这里这样写结果也一样？因为 getInstance 是静态方法，拿到的还是最开始的自己...
        EnumStarvingSingleton enumStarvingSingleton = (EnumStarvingSingleton) constructor3.newInstance();
        System.out.println(enumStarvingSingleton.getInstance());
    }

}
