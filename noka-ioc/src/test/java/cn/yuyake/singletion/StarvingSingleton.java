package cn.yuyake.singletion;

/**
 * create by yeah on 2021/4/3 17:30
 */
public class StarvingSingleton {

    private static final StarvingSingleton starvingSingleton = new StarvingSingleton();

    private StarvingSingleton() {
    }

    public static StarvingSingleton getInstance() {
        return starvingSingleton;
    }

}
