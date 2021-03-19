package cn.yuyake.core.util;

/**
 * 线程工具类
 * <p>
 * create by yeah on 2021/3/18 20:06
 */
public class ThreadUtils {

    /**
     * 线程暂停执行
     *
     * @param millis 暂停毫秒数
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
