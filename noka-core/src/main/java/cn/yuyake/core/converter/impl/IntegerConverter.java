package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;

/**
 * Integer转化器
 * <p>
 * create by yeah on 2021/3/23 14:35
 */
@TemplateConverter({int.class, Integer.class})
public class IntegerConverter extends AbstractConverter<Integer> {

    @Override
    protected Integer convert(String value) throws Exception {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            // 再次匹配，eg. 1_000_000，1,000,000，1 000 000
            return Integer.parseInt(value.trim().replaceAll("[_, ]", ""));
        }
    }

    @Override
    public String buildErrorMsg() {
        return "不是一个int类型的值";
    }
}
