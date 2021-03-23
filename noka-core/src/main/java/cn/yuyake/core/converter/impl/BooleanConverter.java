package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;

/**
 * create by yeah on 2021/3/23 10:00
 */
@TemplateConverter({boolean.class, Boolean.class})
public class BooleanConverter extends AbstractConverter<Boolean> {

    @Override
    protected Boolean convert(String value) throws Exception {
        switch (value.trim().toLowerCase()) {
            case "1":
            case "true":
            case "yes":
            case "ok":
            case "y":
            case "on":
            case "是":
            case "对":
            case "真":
            case "正确":
                return Boolean.TRUE;
            default:
                return Boolean.FALSE;
        }
    }

    @Override
    public String buildErrorMsg() {
        return "不是一个Boolean类型的值";
    }
}
