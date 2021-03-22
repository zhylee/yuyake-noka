package cn.yuyake.core.lang.impl;

import cn.yuyake.core.lang.base.LongList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LongList实现类，具体逻辑类似IntArrayList
 * <p>
 * create by yeah on 2021/3/20 15:59
 */
public class LongArrayList implements LongList, RandomAccess {

    private static final int DEFAULT_CAPACITY = 10;

    private static final long[] EMPTY_ELEMENT_DATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private long[] elementData;

    private int size = 0;

    public LongArrayList() {
        this.elementData = EMPTY_ELEMENT_DATA;
    }

    public LongArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new long[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public LongArrayList(long[] array) {
        this.elementData = array;
        this.size = elementData.length;
    }

    public void trimToSize() {
        if (size < elementData.length) {
            elementData = (size == 0) ? EMPTY_ELEMENT_DATA : Arrays.copyOf(elementData, size);
        }
    }



    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(long o) {
        return indexOf(o) >= 0;
    }

    @Override
    public long[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public List<Long> toList() {
        return null;
    }

    @Override
    public long random() {
        return elementData[ThreadLocalRandom.current().nextInt(0, size)];
    }

    @Override
    public int indexOf(long o) {
        for (int i = 0; i < size; i++) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(long o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public long get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elementData[index];
    }

    @Override
    public boolean add(long o) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = o;
        return true;
    }

    @Override
    public boolean remove(long o) {
        for (int index = 0; index < size; index++) {
            if (o == elementData[index]) {
                int numMoved = size - index - 1;
                if (numMoved > 0) {
                    System.arraycopy(elementData, index + 1, elementData, index, numMoved);
                }
                elementData[--size] = 0;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean addAll(long[] a) {
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }


    @Override
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            hashCode = 31 * hashCode + Long.hashCode(elementData[i]);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LongList)) {
            return false;
        }

        LongList target = (LongList) o;
        // 数量不等，直接就是不等于
        if (this.size() != target.size()) {
            return false;
        }

        // 有一个不等于就是不等
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != target.get(i)) {
                return false;
            }
        }
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENT_DATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        if (minCapacity - elementData.length > 0) {
            // overflow-conscious code
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            if (newCapacity - MAX_ARRAY_SIZE > 0) {
                if (minCapacity < 0) {
                    throw new OutOfMemoryError();
                }
                newCapacity = (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
            }
            // minCapacity is usually close to size, so this is a win:
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
}
