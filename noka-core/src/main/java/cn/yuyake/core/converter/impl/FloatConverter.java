package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;

/**
 * create by yeah on 2021/3/23 14:47
 */
@TemplateConverter({Float.class, float.class})
public class FloatConverter extends AbstractConverter<Float> {

    @Override
    public Float convert(String value) {
        return Float.parseFloat(value.replaceAll("[_ ]", ""));
    }

    @Override
    public String buildErrorMsg() {
        return "不是一个Float类型的值";
    }
}