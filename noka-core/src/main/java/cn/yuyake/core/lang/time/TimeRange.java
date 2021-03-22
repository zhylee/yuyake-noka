package cn.yuyake.core.lang.time;

import cn.yuyake.core.lang.base.IntRange;
import cn.yuyake.core.lang.util.BracketParser;
import java.time.LocalDateTime;

/**
 * 通用时间范围条件判断
 * <p> 格式：[年][月][日][星期][时间]
 * <p> eg. [*][*][*][*][00:00-24:00] / [2021][5-6][11,12,15-19][w1,w5-w7][12:00-13:00]
 * <p>
 * create by yeah on 2021/3/22 10:39
 */
public class TimeRange {

    // 年
    private IntRange year;
    // 月
    private IntRange month;
    // 日
    private IntRange day;
    // 星期
    private IntRange dayOfWeek;
    // 时间
    private LocalTimeSection timeSection;

    public TimeRange(String expression) {
        BracketParser parser = new BracketParser(expression);
        this.year = new IntRange(parser.readString());
        this.month = new IntRange(parser.readString());
        this.day = new IntRange(parser.readString());
        this.dayOfWeek = new IntRange(parser.readString());
        // TODO 时间转化比较复杂，单独创建一个 LocalTimeSection 转化器
        this.timeSection = new LocalTimeSection();
    }

    // 校验是否通配
    public boolean isValid(LocalDateTime time) {
        if (!year.contains(time.getYear())) {
            return false;
        }
        if (!month.contains(time.getMonthValue())) {
            return false;
        }
        if (!day.contains(time.getDayOfMonth())) {
            return false;
        }
        if (!dayOfWeek.contains(time.getDayOfWeek().getValue())) {
            return false;
        }
        return timeSection.isValid(time.toLocalTime());
    }
}
