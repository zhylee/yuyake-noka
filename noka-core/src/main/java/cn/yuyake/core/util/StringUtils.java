package cn.yuyake.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具类
 * <p>
 * create by yeah on 2021/3/20 16:54
 */
public class StringUtils {

    // 空字符串
    public static final String EMPTY = "";
    // 空格
    public static final String SPACE = " ";
    // 换行
    public static final String LF = "\n";
    // 回车
    public static final String CR = "\r";
    // 逗号
    public static final String COMMA = ",";
    // 连字符
    public static final String HYPHEN = "-";
    // 星号
    public static final String ASTERISK = "*";
    // 分号
    public static final String SEMICOLON = ";";
    // 空字符串数组
    public static final String[] EMPTY_STRING_ARRAY = {};
    // 左中括号
    public static final char LM_BRACKET = '[';
    // 右中括号
    public static final char RM_BRACKET = ']';

    // 字符串判空（空格不为空）
    public static boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

    // 字符串判空（空格为空）
    public static boolean isBlank(final String text) {
        if (text == null) {
            return true;
        }
        for (int i = 0, len = text.length(); i < len; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 字符串切割
    public static String[] split(final String str, final String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    /**
     * 字符串切割
     *
     * @param str               要切割的字符串，可为空
     * @param separatorChars    切割匹配符号
     * @param max               切割多少个，-1全切割
     * @param preserveAllTokens true 相邻分隔符进入字符串数组（一般为false，分隔符不作为被匹配对象）
     * @return 匹配完的字符串数组
     */
    private static String[] splitWorker(final String str, final String separatorChars,
        final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        }
        final int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        if (separatorChars == null) { // 匹配符号为null，以空格逐字切割
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) { // 如果当前是空格
                    if (match || preserveAllTokens) { // 如果匹配了或者token为true
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) { // 匹配符为一个字符串，遇见则切割
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        } else { // 匹配符为复杂字符串，符合匹配符任意一个则切割
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        if (sizePlus1++ == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    }
                    start = ++i;
                    continue;
                }
                lastMatch = false;
                match = true;
                i++;
            }
        }
        final boolean flag = preserveAllTokens && lastMatch;
        if (match || flag) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[0]);
    }


    public static void main(String[] args) {
        var str = "aaa;;;;;  bbb; ccc";
        var list = splitWorker(str, ";", 888, false);
        System.out.println(List.of(list));
    }

}
