package cn.yuyake.core.lang.base;

/**
 * IntRange 内部使用的int数字区间
 * <p>
 * create by yeah on 2021/3/22 15:41
 */
class IntSection {

    private final int min;
    private final int max;

    public IntSection(int value) {
        this(value, value);
    }

    public IntSection(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean contains(int element) {
        return min <= element && element <= max;
    }

    @Override
    public String toString() {
        return "IntSection [min = " + min + ", max = " + max + "]";
    }
}
