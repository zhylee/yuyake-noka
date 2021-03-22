package cn.yuyake.core.lang.base;

import java.util.List;

/**
 * 封装 List Long，参见 IntList
 * <p>
 * create by yeah on 2021/3/20 15:54
 */
public interface LongList {

    int size();

    boolean isEmpty();

    boolean contains(long o);

    long[] toArray();

    List<Long> toList();

    long random();

    int indexOf(long o);

    int lastIndexOf(long o);

    long get(int index);

    boolean add(long o);

    boolean remove(long o);

    void clear();

    boolean addAll(long[] a);
}
