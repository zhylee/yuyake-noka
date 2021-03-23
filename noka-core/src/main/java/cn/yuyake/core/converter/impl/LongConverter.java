package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;

/**
 * Long转化器
 * <p>
 * create by yeah on 2021/3/23 14:42
 */
@TemplateConverter({long.class, Long.class})
public class LongConverter extends AbstractConverter<Long> {

    @Override
    public Long convert(String value) {
        return Long.parseLong(value.replaceAll("[_, ]", ""));
    }

    @Override
    public String buildErrorMsg() {
        return "不是一个long类型的值";
    }
}
