package yyolcu2018;
public interface TCollection<E> extends TIterable<E> {
    boolean add(E e);
    void clear();
    boolean contains(Object o);
    boolean equals(Object o);
    boolean isEmpty();
    TIterator<E> iterator();
    boolean remove(Object o);
    int size();
    void sort();
    Object[] toArray();    
}