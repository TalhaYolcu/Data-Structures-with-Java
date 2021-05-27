package yyolcu2018;

public interface TList<E> extends TCollection<E> {
    boolean add(E e);
    void add(int index,E element);
    void clear();
    boolean contains(Object o);
    boolean equals(Object o);
    E get(int index);
    int indexOf(Object o);
    boolean isEmpty();
    TIterator<E> iterator();
    int lastIndexOf(Object o);
    E remove(int index);
    boolean remove(Object o);
    E set(int index,E element);
    int size();
    void sort();
    Object[] toArray();

}