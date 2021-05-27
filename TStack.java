package yyolcu2018;

import java.util.*;

public class TStack<E> {
    private E[] data;
    private int size;
    private static final int CAP=10;
    @SuppressWarnings("unchecked")
    private void reallocate() {
        Object[] o=new Object[size*2];
        for(int i=0;i<size;i++) {
            o[i]=data[i];
        }
        data=null;
        data= (E[])new Object[size*2];
        for(int i=0;i<size;i++) {
            data[i]=(E) o[i];
        }
    }
    public TStack() {
        this(CAP);
    }
    @SuppressWarnings("unchecked")
    public TStack(int len) {
        data=(E[]) new Object[len];
        size=0;
    }
    public boolean empty() {
        return size==0;
    }
    public E peek() {
        if(data==null) {
            throw new NullPointerException();
        }
        if(size==0) {
            throw new NoSuchElementException();
        }
        if(data[0]==null) {
            return null;
        }
        else {
            return data[0];
        }
    }
    public E pop() {
        if(data==null) {
            throw new NullPointerException();
        }
        if(data[0]==null) {
            return null;
        }
        else {
            E ret=data[0];
            for(int i=1;i<size;i++) {
                data[i-1]=data[i];
            }
            size--;
            return ret;
        }       
    }
    public E push(E obj) {
        if(size>=data.length-1) {
            reallocate();
        }
        for(int i=size;i>=0;i--) {
            data[i+1]=data[i];
        }
        data[0]=obj;
        size++;
        return data[0];
    }
    public String toString() {
        StringBuilder str=new StringBuilder();
        for(int i=0;i<size;i++) {
            str.append(data[i]);
            if(i!=size-1) {
                str.append("=>");
            }
        }
        return str.toString();
    }
}