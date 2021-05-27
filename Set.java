import java.util.Collection;

public interface Set<E> {
   //Adds item if it is not already in the set
    boolean add(E obj);
    //Adds all of the elements in collection to this set
    boolean addAll(Collection<E> coll);
    //returns true if this set contains an element that is equal to obj
    boolean contains(Object obj);
    //Returns true if this set contains all of the elements in the coll
    boolean containsAll(Collection<E> coll);
    //returns true if set is empty
    boolean isEmpty();
    //returns an iterator over the elements in the set
    Iterator<E> iterator();
    //Removes the element if it is present
    boolean remove(Object obj);
    //Removes from this set all of coll's elements.
    boolean removeAll(Collection<E> coll);
    //Retains only the elements in this set.
    boolean retainAll(Collection<E> coll);
    //Returns the number of elements in the set
    int size();
}