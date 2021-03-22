package cn.yuyake.core.lang.impl;

import cn.yuyake.core.lang.base.DoubleList;
import java.util.Arrays;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;

/**
 * create by yeah on 2021/3/20 16:24
 */
public class DoubleArrayList implements DoubleList, RandomAccess {

    private static final int DEFAULT_CAPACITY = 10;

    private static final double[] EMPTY_ELEMENT_DATA = {};

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private double[] elementData;

    private int size = 0;

    public DoubleArrayList() {
        this.elementData = EMPTY_ELEMENT_DATA;
    }

    public DoubleArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new double[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public DoubleArrayList(double[] array) {
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
    public boolean contains(double o) {
        return indexOf(o) >= 0;
    }

    @Override
    public double[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public List<Double> toList() {
        return null;
    }

    @Override
    public double random() {
        return elementData[ThreadLocalRandom.current().nextInt(0, size)];
    }

    @Override
    public int indexOf(double o) {
        for (int i = 0; i < size; i++) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(double o) {
        for (int i = size - 1; i >= 0; i--) {
            if (o == elementData[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public double get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elementData[index];
    }

    @Override
    public boolean add(double o) {
        ensureCapacityInternal(size + 1);
        elementData[size++] = o;
        return true;
    }

    @Override
    public boolean remove(double o) {
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
    public boolean addAll(double[] a) {
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elementData);
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DoubleArrayList other = (DoubleArrayList) obj;
        if (!Arrays.equals(elementData, other.elementData)) {
            return false;
        }
        return size == other.size;
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
