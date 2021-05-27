package yyolcu2018;

import java.util.NoSuchElementException;

import yyolcu2018.TQueueInt;

public class TArrayQueue<E> implements TQueueInt<E> {
    private E[] data;
    private static final int INITIAL_CAPACITY=10;
    private int front;
    private int rear;
    private int cap;
    private int size;
    public TArrayQueue() {
        this(INITIAL_CAPACITY);
    }
    @SuppressWarnings("unchecked")
    public TArrayQueue(int init) {
        if(init<0) {
            throw new IllegalArgumentException();
        }
        cap=init;
        data=(E[]) new Object[cap];
        front=0;
        rear=cap-1;
        size=0;
    }
    @SuppressWarnings("unchecked")
    private void reallocate() {
        int newcap=2*cap;
        E[] newdata=(E[]) new Object[newcap];
        int j=front;
        for(int i=0;i<size;i++) {
            newdata[i]=data[j];
            j=(j+1)%cap;
        }
        front=0;
        rear=size-1;
        cap=newcap;
        data=newdata;
    }
    public boolean offer(E item) {
        if(size==cap) {
            reallocate();
        }
        size++;
        rear=(rear+1)%cap;
        data[rear]=item;
        return true;
    }
    public E remove() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        front=(front+1)%cap;
        return data[front-1];
    }
    public E poll() {
        if(isEmpty()) {
            return null;
        }
        E result=data[front];
        front=(front+1)%cap;
        size--;
        return result;
    }
    public E peek() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[front];
    }
    public E element() {
        if(isEmpty()) {
            return null;
        }
        return data[front];
    }
    @SuppressWarnings("unchecked")
    public boolean add(Object e) {
        offer((E)e);
        return true;
    }   
    public void clear() {
        data=null;
        size=0;
        cap=0;
    }
    public boolean contains(Object o) {
        for(int i=0;i<size;i=(i+1)%cap) {
            if(data[i].equals(o)) {
                return true;
            }
        }
        return false;
    }
    public boolean equals(Object o) {
        for(int i=0;i<size;i=(i+1)%cap) {
            if(!data[i].equals(o)) {
                return false;
            }
        }
        return true;        
    }
    public boolean isEmpty() {
        return size==0;
    }
    public TIterator<E> iterator() {
        return new TIter();
    }
    private class TIter implements TIterator<E> {
        private int index;
        private int count=0;
        public TIter() {
            index=front;
        }
        public boolean hasNext() {
            return count<size;
        }
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            E ret=data[index];
            index=(index+1)%cap;
            count++;
            return ret;
        }
        public void remove() {
            throw new UnsupportedOperationException("IN QUEUE, WE DON'T DO THAT HERE");
        }
    }
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    public int size() {
        return size;
    }
    public void sort() {

    }
    public Object[] toArray() {
        return new Object[cap];
    }
}