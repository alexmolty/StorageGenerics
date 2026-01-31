public interface IMyArray<E> {
    boolean add(E obj);
    boolean add(int index, E obj);

    boolean set(int index, E obj);
    E get(int index);
    int size();

    int indexOf(E obj);
    int lastIndexOf(E obj);

    E remove(int index);
    boolean remove(E obj);

    boolean contains(E obj);
    Object[] toArray();

    int getCapacity();
}