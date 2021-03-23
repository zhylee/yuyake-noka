package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;
import cn.yuyake.core.lang.base.FloatList;
import cn.yuyake.core.lang.impl.FloatArrayList;
import cn.yuyake.core.util.StringUtils;
import java.util.Arrays;

/**
 * create by yeah on 2021/3/23 14:50
 */
@TemplateConverter({FloatList.class, FloatArrayList.class})
public class FloatListConverter extends AbstractConverter<FloatList> {

    @Override
    public FloatList convert(String value) {
        if (StringUtils.isEmpty(value)) {
            return new FloatArrayList();
        }

        String[] array = StringUtils.split(value, ",");
        FloatArrayList result = new FloatArrayList(array.length);
        Arrays.stream(array).forEach(v -> result.add(Float.parseFloat(v)));
        return result;
    }

    @Override
    public String buildErrorMsg() {
        return "Float类型的数组应该是以英文逗号分隔的，如：1.0,3.2,4";
    }
}
