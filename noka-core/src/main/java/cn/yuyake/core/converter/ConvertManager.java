package cn.yuyake.core.converter;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.impl.BooleanConverter;
import cn.yuyake.core.converter.impl.FloatConverter;
import cn.yuyake.core.converter.impl.FloatListConverter;
import cn.yuyake.core.converter.impl.IntListConverter;
import cn.yuyake.core.converter.impl.IntegerConverter;
import cn.yuyake.core.converter.impl.LongConverter;
import cn.yuyake.core.converter.impl.StringConverter;
import cn.yuyake.core.util.ClassUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板转换实现管理类
 * <p> 内置一些系统级别的转化功能，只拥有简单的转化，依旧是单例模式；可后续添加注册
 * <p> TODO 参考spring手撸一套再参考这里的Ioc
 * <p>
 * create by yeah on 2021/3/23 14:27
 */
public class ConvertManager {

    private static final ConvertManager INSTANCE = new ConvertManager();
    private static final Map<Class<?>, Converter<?>> CONVERTERS = new HashMap<>(128);

    private ConvertManager() {
    }

    public static ConvertManager getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE.register(BooleanConverter.class);
        INSTANCE.register(IntegerConverter.class);
        INSTANCE.register(LongConverter.class);
        INSTANCE.register(StringConverter.class);
        INSTANCE.register(FloatConverter.class);
        INSTANCE.register(IntListConverter.class);
        INSTANCE.register(FloatListConverter.class);
        // 使用了IOC功能，有自动扫描功能了，就不再需要手工添加...
    }

    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Class<T> type) {
        return (Converter<T>) CONVERTERS.get(type);
    }

    // 注册一个模板转化实现类，严格判断
    public void register(Class<?> klass, TemplateConverter templateConverter, Object single) {
        if (!(single instanceof Converter<?>)) {
            throw new RuntimeException("非法的转化器." + klass.getName());
        }
        this.putConvert((Converter<?>) single, templateConverter);
    }

    // 系统内部注册，不判定注解
    private void register(Class<? extends Converter<?>> klass) {
        this.putConvert(ClassUtils.newInstance(klass),
            klass.getAnnotation(TemplateConverter.class));
    }

    private void putConvert(Converter<?> converter, TemplateConverter annotation) {
        for (Class<?> targetClass : annotation.value()) {
            CONVERTERS.put(targetClass, converter);
        }
    }
}
