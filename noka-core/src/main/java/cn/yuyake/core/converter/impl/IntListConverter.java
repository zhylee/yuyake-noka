package cn.yuyake.core.converter.impl;

import cn.yuyake.core.annotation.TemplateConverter;
import cn.yuyake.core.converter.AbstractConverter;
import cn.yuyake.core.converter.Converter;
import cn.yuyake.core.lang.base.IntList;
import cn.yuyake.core.lang.impl.IntArrayList;
import cn.yuyake.core.util.StringUtils;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * create by yeah on 2021/3/23 14:47
 */
@TemplateConverter({IntList.class, IntArrayList.class})
public class IntListConverter extends AbstractConverter<IntList> {

    @Override
    protected IntList convert(String value) throws Exception {
        if (StringUtils.isEmpty(value)) {
            return new IntArrayList();
        }
        String[] array = StringUtils.split(value, ",");
        IntList result = new IntArrayList(array.length);
        for (String s : array) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    @Override
    public IntList convert(Field field, Map<String, String> data) throws Exception {
        if (data.isEmpty()) {
            return new IntArrayList();
        }
        return new IntArrayList(data.values().stream().mapToInt(Integer::parseInt).toArray());
    }

    @Override
    public String buildErrorMsg() {
        return "数字类型的数组应该是以英文逗号分隔的，如：1,2,3,4";
    }
}
