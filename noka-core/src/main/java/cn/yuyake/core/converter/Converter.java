package cn.yuyake.core.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * 转化器接口
 * <p>
 * create by yeah on 2021/3/22 16:42
 */
public interface Converter<T> {

    // 根据类的属性转化字符串
    T convert(Field field, String value) throws Exception;

    // 根据方法的参数转化字符串
    T convert(Parameter parameter, String value) throws Exception;

    // 根据类的属性转化一组字符串
    T convert(Field field, Map<String, String> data) throws Exception;

    // 构建错误提示
    public String buildErrorMsg();

}
