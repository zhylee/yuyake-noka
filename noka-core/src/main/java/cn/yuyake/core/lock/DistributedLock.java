package cn.yuyake.core.lock;

import cn.yuyake.core.util.ThreadUtils;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁
 *
 * create by yeah on 2021/3/19 14:24
 */
public abstract class DistributedLock implements Lock, AutoCloseable {

    @Override
    public void close() throws Exception {
        this.unlock();
    }

    // 尝试拿锁，拿不到则等待
    @Override
    public void lock() {
        while (!tryLock()) {
            ThreadUtils.sleep(100);
        }
    }

    // 暂不实现的方案
    @Override
    public void lockInterruptibly() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        return null;
        // throw new UnsupportedOperationException();
    }
}
