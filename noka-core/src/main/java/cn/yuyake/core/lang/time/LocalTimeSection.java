package cn.yuyake.core.lang.time;

import cn.yuyake.core.util.StringUtils;
import java.time.LocalTime;

/**
 * 仅对时间节点进行匹配 LocalTime
 * <p>
 * create by yeah on 2021/3/22 10:42
 */
public class LocalTimeSection {

    private final boolean flag;
    private final long startTime; // 纳秒判断，0 ~ 24 * 60 * 60 * 1,000,000,000 - 1
    private final long endTime;

    public LocalTimeSection() {
        this(true, null, null);
    }

    public LocalTimeSection(LocalTime startTime, LocalTime endTime) {
        this(false, startTime, endTime);
    }

    private LocalTimeSection(boolean flag, LocalTime startTime, LocalTime endTime) {
        this.flag = flag;
        this.startTime = startTime == null ? 0L : startTime.toNanoOfDay();
        this.endTime = endTime == null ? 0L : endTime.toNanoOfDay();
    }

    public boolean isValid(LocalTime localTime) {
        if (flag) {
            return true;
        }
        var now = localTime.toNanoOfDay();
        if (endTime >= startTime) {
            // 当日情况，startTime < localTime < endTime
            return startTime <= now && now <= endTime;
        } else {
            // 跨天情况，endTime < localTime < startTime
            return now >= startTime || now <= endTime;
        }
    }
}
