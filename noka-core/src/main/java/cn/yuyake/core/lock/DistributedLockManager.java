package cn.yuyake.core.lock;

import java.io.Serializable;

/**
 * 分布式锁管理器
 * <p>
 * create by yeah on 2021/3/19 17:17
 */
public interface DistributedLockManager {

    /**
     * 根据指定ID获取一把分布式锁.
     * <p>需要调用者自己获取锁与释放锁
     *
     * @param id 指定ID
     * @return 返回分布式锁
     */
    DistributedLock getLock(Serializable id);
}
