package cn.yuyake.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解 模板转换实现类
 * <p> 被注解的类必须实现 Converter 接口
 * <p>
 * create by yeah on 2021/3/23 10:01
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateConverter {

    // 转化后的类型
    Class<?>[] value();
}
