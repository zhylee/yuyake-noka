package cn.yuyake.core.lang.impl;

import cn.yuyake.core.lang.base.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * create by yeah on 2021/3/20 15:03
 */
public class IntArrayList implements IntList, RandomAccess {

    // 默认容量
    private static final int DEFAULT_CAPACITY = 10;
    // 共享的空数组
    private static final int[] EMPTY_ELEMENT_DATA = {};
    // 默认最大容量，一些VM会在数组中保留head words，分配太大的数组可能导致 OutOfMemoryError
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    // 实际数组
    private int[] elementData;
    // 数组大小
    private int size = 0;

    public IntArrayList() {
        this.elementData = EMPTY_ELEMENT_DATA;
    }

    public IntArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new int[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public IntArrayList(int[] array) {
        this.elementData = array;
        this.size = elementData.length;
    }

    /**
     * 调整大小，将elementData多余的数据舍弃，最小化IntArrayList实例的存储量
     */
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
    public boolean contains(int o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public List<Integer> toList() {
        final List<Integer> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(elementData[i]);
        }
        return result;
    }

    @Override
    public int random() {
        return elementData[ThreadLocalRandom.current().nextInt(0, size)];
    }

    @Override
    public int indexOf(int o) {
        for (int i = 0; i < size; i++) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(int o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elementData[index];
    }

    @Override
    public boolean add(int o) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = o;
        return true;
    }

    @Override
    public boolean remove(int o) {
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
    public int hashCode() {
        int hashCode = 1;
        for (int i = 0; i < size; i++) {
            hashCode = 31 * hashCode + Integer.hashCode(elementData[i]);
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntList)) {
            return false;
        }

        IntList target = (IntList) obj;
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

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    @Override
    public boolean addAll(int[] a) {
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    public void forEach(Consumer<? super Integer> action) {
        Objects.requireNonNull(action);
        final int size = this.size;
        for (int i = 0; i < size; i++) {
            action.accept(elementData[i]);
        }
    }

    // 确保还有足够的容量来添加数据
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENT_DATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        if (minCapacity - elementData.length > 0) {
            // overflow-conscious code
            int oldCapacity = elementData.length;
            int newCapacity = oldCapacity + (oldCapacity >> 1); // 新扩容=旧的长度+旧的长度/2（二进制右移一位相当于除以2）
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            if (newCapacity - MAX_ARRAY_SIZE > 0) {
                if (minCapacity < 0) {
                    throw new OutOfMemoryError();
                }
                // 实际扩容值若还是超过设定的最大值，只要最小扩容值不超过实际最大值，还是给予设定
                newCapacity = (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
            }
            // minCapacity is usually close to size, so this is a win:
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
}
