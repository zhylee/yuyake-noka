package cn.yuyake.core.util;

import java.lang.reflect.InvocationTargetException;

/**
 * create by yeah on 2021/3/23 14:32
 */
public class ClassUtils {

    public static <T> T newInstance(final Class<T> klass) {
        try {
            return klass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("无法创建实例. Class=" + klass.getName(), e);
        }
    }
}
