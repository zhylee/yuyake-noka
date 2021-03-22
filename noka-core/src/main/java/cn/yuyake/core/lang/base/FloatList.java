package cn.yuyake.core.lang.base;

import java.util.List;

/**
 * 封装 List Float，参见 IntList
 * <p>
 * create by yeah on 2021/3/20 16:36
 */
public interface FloatList {

    int size();

    boolean isEmpty();

    boolean contains(float o);

    float[] toArray();

    List<Float> toList();

    float random();

    int indexOf(float o);

    int lastIndexOf(float o);

    float get(int index);

    boolean add(float o);

    boolean remove(float o);

    void clear();

    boolean addAll(float[] a);

}
