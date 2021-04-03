package cn.yuyake.singletion;

/**
 * create by yeah on 2021/4/3 17:32
 */
public class LazyDoubleCheckSingleton {

    // volatile 不能和 final 共用，此字段能保证创建实例时会按照1，2，3的方式进行分配，不会乱序
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton() {
    }

    public static LazyDoubleCheckSingleton getInstance() {
        // 第一次检测
        if (instance == null) {
            // 同步
            synchronized (LazyDoubleCheckSingleton.class) {
                if (instance == null) {
                    // 1. 分配对象内存空间
                    // memory = allocate();
                    // 2. 初始化对象
                    // instance(memory);
                    // 3. 设置instance指向刚分配的内存地址，此时instance != null
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
