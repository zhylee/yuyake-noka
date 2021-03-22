package cn.yuyake.core.lang.base;

import java.util.List;

/**
 * 封装 List Double，参见 IntList
 * <p>
 * create by yeah on 2021/3/20 16:09
 */
public interface DoubleList {

    int size();

    boolean isEmpty();

    boolean contains(double o);

    double[] toArray();

    List<Double> toList();

    double random();

    int indexOf(double o);

    int lastIndexOf(double o);

    double get(int index);

    boolean add(double o);

    boolean remove(double o);

    void clear();

    boolean addAll(double[] a);

}
