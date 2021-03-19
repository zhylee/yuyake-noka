package cn.yuyake.core;

import cn.yuyake.core.lock.LockManager;

/**
 * create by yeah on 2021/3/19 17:14
 */
public class Test {

    public static void main(String[] args) {
        LockManager.getLock(new Throwable());
    }
}
