package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;

/**
 * 字符串转化器
 * <p> 我就是我。。。
 * <p>
 * create by yeah on 2021/3/23 14:43
 */
@TemplateConverter(String.class)
public class StringConverter extends AbstractConverter<String> {

    @Override
    public String convert(String value) {
        return value;
    }

    @Override
    public String buildErrorMsg() {
        return "不是一个字符串";
    }
}
