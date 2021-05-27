package yyolcu2018;

public interface TQueueInt<E> extends TCollection<E> {
    boolean offer(E item);
    E remove();
    E poll();
    E peek();
    E element();
}