package cn.yuyake.core.lock;

import cn.yuyake.core.lang.collect.TimeoutHashMap;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 锁工具类
 * <p>典型的单例模式实现，任意可序列化的key可以获取其唯一对应的object。
 * <p>编译后的字节码：零长度的byte数组对象的生成只要3行操作码，new Object()需要7行操作码
 * <p>因此零长度的byte数组对象创建起来将比任何对象都经济，TODO 上述逻辑待验证
 * create by yeah on 2021/3/19 14:56
 */
public final class LockManager {

    // 5分钟的缓存时间
    private static final int DURATION = 5;

    private static final TimeoutHashMap<Serializable, byte[]> LOCKER_STORE;

    static {
        LOCKER_STORE = new TimeoutHashMap<>(DURATION, TimeUnit.MINUTES, () -> new byte[0]);
    }

    private LockManager() {
    }

    public static Object getLock(Serializable id) {
        return LOCKER_STORE.get(id);
    }
}
