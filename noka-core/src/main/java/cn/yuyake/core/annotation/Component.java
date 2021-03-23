package cn.yuyake.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IOC注解，类似spring
 * <p> eg. GM指令处理器、有多种实现的类...
 * <p> // TODO Ioc是否需要自己造轮子，而且里面有些比较陈旧，还用着JAVAEE的注解
 * <p>
 * create by yeah on 2021/3/23 10:05
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    // 这个类的唯一ID
    int[] id() default {};

    // 这个类的唯一名称
    String[] name() default {};
}
