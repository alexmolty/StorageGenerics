import java.util.Arrays;

public class MyArray<E> implements IMyArray<E> {
    private static final int CAPACITY = 16;
    private Object[] array;
    private int size = 0;

    public MyArray(int CAPACITY) {
        array = new Object[CAPACITY];
    }

    public MyArray() {
        this(CAPACITY);
    }

    public int getCapacity() {
        return array.length;
    }

    @Override
    public boolean add(E obj) {
        if (obj == null) return false;
        if (size == array.length) {
            allocateArray();
        }
        array[size++] = obj;
        return true;
    }

    private void allocateArray() {
        array = Arrays.copyOf(array, (array.length +2) * 2);
    }

    @Override
    public boolean add(int index, E obj) {
        if (index < 0 || index > size) {
            return false;
        }
        if (index == size) {
            return add(obj);
        }
        if (size == array.length) {
            allocateArray();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = obj;
        size++;
        return true;
    }

    @Override
    public boolean set(int index, E obj) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array[index] = obj;
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (E) array[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(E obj) {
        if (obj == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (obj.equals(array[i])) return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E obj) {
        if (obj == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (array[i] == null) return i;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (obj.equals(array[i])) return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E res = (E) array[index];
        if (index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }
        array[--size] = null;
        return res;
    }

    @Override
    public boolean remove(E obj) {
        int index = indexOf(obj);
        if (index < 0) return false;
        remove(index);
        return true;
    }


    @Override
    public boolean contains(E obj) {
        return indexOf(obj) >= 0;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }


    @SuppressWarnings("unchecked")
    public boolean addAll(MyArray<E> other) {
        // вставляем в конец массива другой массив
        if (other == null) return false;
        if (other.size == 0) return true;
        for (E obj : (E[]) other.toArray()) {
            add(obj);
        }
        return true;
    }

    @SafeVarargs
    public final boolean addAll(E... elements) {
        // вставляем в конец массива varargs elements
        if (elements == null) return false;
        for (E e : elements) {
            add(e);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean addAll(int index, MyArray<E> other) {
        // вставляем другой массив по индексу
        if (other == null) return false;
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (other.size == 0) return true;
        int otherSize = other.size();
        while (size + otherSize > array.length) {
            allocateArray();
        }
        System.arraycopy(array, index, array, index + otherSize, size - index); // освобождаем место
        System.arraycopy(other.array, 0, array, index, otherSize); // вставляем элементы
        size += otherSize;
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean removeAll(MyArray<E> other) {
        // удалить переданные элементы, пример:
        // исходный массив[1,1,3,4,5], other[1,3,8] => [1,4,5]
        if (other == null) return false;
        if (other.size == 0) return true;
        for (E obj : (E[]) other.toArray()) {
            remove(obj);
        }
        return true;
    }
}
