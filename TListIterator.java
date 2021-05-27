package yyolcu2018;
public interface TListIterator<E> extends TIterator<E> {
    void add(E e);
    boolean hasNext();
    boolean hasPrevious();
    E next();
    int nextIndex();
    E previous();
    int previousIndex();
    void remove();
    void set(E e);
}