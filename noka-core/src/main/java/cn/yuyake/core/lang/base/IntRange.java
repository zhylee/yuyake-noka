package cn.yuyake.core.lang.base;

import cn.yuyake.core.util.StringUtils;
import java.util.LinkedList;
import java.util.List;

/**
 * 配置int表示的集合，通常以 [*] [11,12,15-19] 进行匹配
 * <p>
 * create by yeah on 2021/3/20 16:50
 */
public class IntRange {

    // 区间列表
    private final List<IntSection> sectionList = new LinkedList<>();
    // 是否全部匹配
    private boolean flag = false;

    public IntRange(String expression) {
        this.analysis(expression);
    }

    public boolean contains(final int element) {
        return flag || sectionList.stream().anyMatch(v -> v.contains(element));
    }

    // 解析表达式
    private void analysis(String expression) {
        if (StringUtils.ASTERISK.equals(expression)) { // * 为全匹配
            this.flag = true;
            return;
        }
        if (StringUtils.isBlank(expression)) { // 为空则均不匹配
            return;
        }
        var list = StringUtils.split(expression, StringUtils.COMMA); // 以 , 的方式对字符串切割
        for (String s : list) {
            if (StringUtils.isBlank(s)) {
                continue;
            }
            String[] array = StringUtils.split(s, StringUtils.HYPHEN); // 以 - 的方式对字符串切割
            if (array.length == 1) {
                this.sectionList.add(new IntSection(parseInt(array[0])));
            } else if (array.length == 2) {
                this.sectionList.add(new IntSection(parseInt(array[0]), parseInt(array[1])));
            } else {
                throw new IllegalArgumentException("数字区间表达式格式错误：" + expression);
            }
        }

    }

    // 将字符串转化为数字
    private Integer parseInt(String data) {
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            // 有一种时间表达式里有星期的配置，需要容错处理... TODO 这是为了兼容[w1-w7]，详见 TimeRange
            return Integer.parseInt(data.substring(1));
        }
    }

}