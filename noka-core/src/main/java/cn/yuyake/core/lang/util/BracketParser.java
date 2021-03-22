package cn.yuyake.core.lang.util;

import cn.yuyake.core.util.StringUtils;

/**
 * 中括号解析器
 * <p>
 * create by yeah on 2021/3/22 15:46
 */
public class BracketParser {

    private final String expression; // 表达式
    private final StringBuilder sb; // 当前字符串流
    private int index = 0; // 访问游标

    public BracketParser(String expression) {
        this.expression = expression;
        this.sb = new StringBuilder(expression.length() / 2);
    }

    // 读出括号内的值
    public String readString() {
        var cur = expression.charAt(index++);
        if (cur != StringUtils.LM_BRACKET) {
            throw new IllegalArgumentException("not left mid bracket");
        }
        sb.setLength(0); // 缓存清 0
        do {
            cur = expression.charAt(index++);
            if (cur == StringUtils.RM_BRACKET) {
                break;
            }
            sb.append(cur);
        } while (expression.length() > index);
        return sb.toString();
    }
}
