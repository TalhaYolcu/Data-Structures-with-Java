import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.*;

public class PriorityQueue<E> 
extends AbstractQueue<E> 
implements Queue<E> {
    private ArrayList<E> data;
    Comparator<E> comparator=null;

    public PriorityQueue() {
        data=new ArrayList<E>();
    }
    public PriorityQueue(int cap,Comparator<E> comp) {
        if(cap<1) {
            throw new IllegalArgumentException();
        }
        data=new ArrayList<>(cap+1);
        comparator=comp;
    }
    public boolean offer(E item) {
        data.add(item);
        int child=data.size()-1;
        int parent=(child-1)/2;
        while(parent>=0 && compare(data.get(parent),data.get(child))>0) {
            swap(parent,child);
            child=parent;
            parent=(child-1)/2;
        }
        return true;
    }
    public E poll() {
        if(isEmpty()) {
            return null;
        }
        E result=data.get(0);
        if(data.size()==1) {
            data.remove(0);
            return result;
        }
        data.set(0,data.remove(data.size()-1));
        int parent=0;
        while(true) {
            int leftchild=2*parent+1;
            if(leftchild>=data.size()) {
                break;
            }
            int rightchild=leftchild+1;
            int minchild=leftchild;
            if(rightchild<data.size() && compare(data.get(leftchild),data.get(rightchild))>0) {
                minchild=rightchild;
            }
            if(compare(data.get(parent),data.get(minchild))>0) {
                swap(parent,minchild);
                parent=minchild;
            }
            else {
                break;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private int compare(E left,E right) {
        if(comparator!=null) {
            return comparator.compare(left,right);
        }
        else {
            return ((Comparable<E>) left).compareTo(right);
        }
    }
    public void swap(int a,int b) {
        E temp=data.get(a);
        data.set(a,data.get(b));
        data.set(b,temp);
    }
    public int size() {
        return data.size();
    }
    public Iterator<E> iterator() {
        return data.iterator();
    }
    public E peek() {
        return data.get(0);
    }
}