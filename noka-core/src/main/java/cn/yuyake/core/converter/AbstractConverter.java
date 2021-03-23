package cn.yuyake.core.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * 抽象实现单字符串的转化器
 * <p>
 * create by yeah on 2021/3/22 16:51
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(Field field, String value) throws Exception {
        return convert(value);
    }

    @Override
    public T convert(Parameter parameter, String value) throws Exception {
        return convert(value);
    }

    @Override
    public T convert(Field field, Map<String, String> data) throws Exception {
        throw new UnsupportedOperationException("AbstractConverter未实现Map类型的配置");
    }

    // 将一个字符串转化成目标对象
    protected abstract T convert(String value) throws Exception;
}
