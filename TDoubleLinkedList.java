package yyolcu2018;
import java.util.*;
public class TDoubleLinkedList<E> implements TList<E> {
    private Node<E> head=null;
    private Node<E> tail=null;
    private int size=0;
    private static class Node<E> {
        private E data;
        private Node<E> next=null;
        private Node<E> prev=null;

        private Node(E item) {
            data=item;
        }
    }
    public boolean add(E entry) {
        add(size,entry);
        return true;
    }
    public void add(int index,E obj) {
        if(index<0 || index>size) {
            throw new IndexOutOfBoundsException();
        }
        else {
            listiterator(index).add(obj);
        }
    }
    public void addFirst(E item) {
        add(0,item);
    }
    public void addLast(E item) {
        add(size,item);
    }
    public void clear() {
        Node<E> node=getNode(0);
        for(int i=0;i<size();i++) {
            node.data=null;
            node=node.next;            
        }
    }    
    public boolean contains(Object o) {
        if(head==null) {
            throw new NullPointerException();
        }
        Node<E> node=getNode(0);
        while(node.next!=null) {
            if(node.data.equals(o)) {
                return true;
            }
        }
        return false;
    }
    public E getFirst() {
        return head.data;
    }
    public E getLast() {
        return tail.data;
    }
    public E get(int index) {
        return listiterator(index).next();
    }
    private Node<E> getNode(int index) {
        Node<E> node=head;
        for(int i=0;i<index && node!=null;i++) {
            node=node.next;
        }
        return node;
    }
    public int indexOf(Object o) {
        int index=0;
        Node<E> node=getNode(index);
        while(true) {
            if(node.data.equals(o)) {
                break;
            }
            else if(node==null) {
                break;
            }
            else {
                node=node.next;
                index+=1;
            }
        }
        return index;
    }
    public boolean isEmpty() {
        return size!=0;
    }
    public TIterator<E> iterator() {
        return new TItr();
    }
    private class TItr implements TIterator<E> {
        int cursor=0;
        int lastRet=-1;
        public boolean hasNext() {
            return cursor!=size;
        }
        @SuppressWarnings("unchecked")
        public E next() {            
            if(cursor>=size()) {
                throw new NoSuchElementException();
            }

            Object[] e=TDoubleLinkedList.this.toArray();
            if(cursor>=e.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            cursor+=1;
            lastRet=cursor-1;
            return (E) e[cursor-1];
        }
        public void remove() {
            if(lastRet<0) {
                throw new IllegalStateException();
            }
            try {
                TDoubleLinkedList.this.remove((int)lastRet);
                cursor=lastRet;
                lastRet=-1;
            }
            catch(IndexOutOfBoundsException ne) {
                throw new ConcurrentModificationException();
            }            
        }
    }
    private class TListItr implements TListIterator<E> {
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index;
        public TListItr(int i) {
            if(i<0 || i>size) {
                throw new IndexOutOfBoundsException();
            }   
            lastItemReturned=null;
            if(i==size) {
                index=size;
                nextItem=null;
            }
            else {
                nextItem=head;
                for(index=0;index<i;index++) {
                    nextItem=nextItem.next;
                }
            }         
        }
        public void add(E obj) {
            if(head==null) {
                head=new Node<E>(obj);
                tail=head;
            }
            else if(nextItem==head) {
                Node<E> newNode=new Node<E>(obj);
                newNode.next=nextItem;
                newNode.prev=newNode;
                head=newNode;
            }
            else if(nextItem==null) {
                Node<E> newNode=new Node<>(obj);
                tail.next=newNode;
                newNode.prev=tail;
                tail=newNode;
            }
            else {
                Node<E> newNode=new Node<>(obj);
                newNode.prev=nextItem.prev;
                nextItem.prev.next=newNode;
                newNode.next=nextItem;
                nextItem.prev=newNode;
            }
            size++;
            index++;
            lastItemReturned=null;
        }
        public boolean hasNext() {
            return nextItem!=null;
        }
        public boolean hasPrevious() {
            //return (nextItem==null && size!=0) || nextItem.prev!=null;
            return nextItem!=head;
        }
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }
        public int nextIndex() {
            return index;
        }
        public int previousIndex() {
            return index-1;
        }
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if(nextItem==null) {
                nextItem=tail;
            }
            else {
                nextItem=nextItem.prev;
            }
            lastItemReturned=nextItem;
            index--;
            return lastItemReturned.data;
        }
        public void remove() {

        }
        public void set(E obj) {
            
        }
    }
    public TListIterator<E> listiterator() {
        return listiterator(0);
    }
    public TListIterator<E> listiterator(int index) {
        if(index<0 || index>size) {
            throw new IndexOutOfBoundsException();
        }
        else {
            return new TListItr(index);
        }
    }
    public int lastIndexOf(Object o) {
        int index=0;
        Node<E> node=getNode(index);
        int cnt=0; 
        while(true) {
            if(index==size) {
                break;
            }
            else if(cnt==size) {
                break;
            }
            else if(node.data.equals(o)) {
                index=cnt;
            }
            else {
                node=node.next;
                cnt+=1;
            }           
        }
        return index;      
    }
    public E remove(int index) {
        Node<E> node=getNode(0);
        E x;
        if(index<0 || index>size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        else if(index==size) {
            return removeLast();
        }
        for(int i=0;i<index-1;i++) {
            node=node.next;
        }
        x=node.next.data;
        node.next=node.next.next;
        size-=1;
        return x;
    }
    public boolean remove(Object o) {
        Node<E> node=getNode(0);
        if(node.data.equals(o)) {
            removeFirst();
        }
        while(true) {
            if(node.next.data.equals(o)) {
                break;
            }
            else if(node==null) {
                return false;
            }
            else {
                node=node.next;
            }
        }
        node.next=node.next.next;
        size-=1;
        return true;
    }
    public E removeLast() {
        Node<E> node=getNode(size-1);
        Node<E> node2=getNode(size);
        E ret=node2.data;
        node.next=null;
        return ret;
    }
    public E removeFirst() {
        Node<E> node=head;
        E x=node.data;
        head=head.next;
        return x;
    }
    public E set(int index,E entry) {
        if(index<0 || index>=size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }      
        Node<E> node=getNode(index);
        E result=node.data;
        node.data=entry;
        return result;  
    } 
    public int size() {
        return size;
    }
    public void sort() {

    }
    public Object[] toArray() {
        Object[] o=new Object[size()];
        Node<E> node=getNode(0);
        for(int i=0;i<size;i++) {
            o[i]=node.data;
            node=node.next;
        }
        return o;
    }
    public String toString() {
        Node<E> nodeRef=getNode(0);
        StringBuilder result=new StringBuilder();
        while(nodeRef!=null) {
            result.append(nodeRef.data);
            if(nodeRef.next!=null) {
                result.append("==>");
            }
            nodeRef=nodeRef.next;
        }
        return result.toString();
    }
}