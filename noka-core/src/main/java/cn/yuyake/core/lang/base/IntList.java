package cn.yuyake.core.lang.base;

import java.util.List;

/**
 * <p>由于Java集合会擦除类型，这里相当于把List Integer封装一下
 * <p>方便编写字符串模板转化为对象的操作，LongList/FloatList/DoubleList类似
 * <p>TODO 可否和 byte[] 一样，使用 int[]/long[]/float[]/double[] 进行解析呢？
 * <p>
 * create by yeah on 2021/3/20 14:42
 */
public interface IntList {

    // 返回列表个数
    int size();

    // 列表是否为空
    boolean isEmpty();

    // 列表是否至少包含一个指定的int值
    boolean contains(int o);

    // 转化为普通的Integer数组
    int[] toArray();

    // 转化为JDK的List Integer
    List<Integer> toList();

    // 随机一个int值
    int random();

    // 查找指定int值的索引，不存在为-1
    int indexOf(int o);

    // 列表中最后出现的指定int值的索引，不存在为-1
    int lastIndexOf(int o);

    // 获取指定下标的int值，不存在为-1
    int get(int index);

    // 将指定的int值添加到此列表的尾部
    boolean add(int o);

    // 移除此列表中首次出现的指定元素
    boolean remove(int o);

    // 清空列表
    void clear();

    // 添加数组
    boolean addAll(int[] a);
}
