package yyolcu2018;

public interface TDeque<E> extends TQueueInt<E> {
    boolean offerFirst(E item);
    boolean offerLast(E item);
    void addFirst(E item);
    void addLast(E item)
    E pollFirst();
    E pollLast();
    E removeFirst();
    E removeLast();
    E peekFirst();
    E peekLast();
    E getFirst();
    E getLast();
    boolean removeFirstOccurence(Object item);
    boolean removeLastOccurence(Object item);
    TIterator<E> iterator();
    
}