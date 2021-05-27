public interface THashMap <K,V> {
    //Returns the value associated with key,return null if key is not present
    V get(Object key);

    //Returns true if this table contains no key value mappings
    boolean isEmpty();

    //Associates the specified value with the specified key. Returns the previous value.
    V put(K key,V value);

    //Removes the mapping for this key from this tabke if it is present
    V remove(Object key);

    //Returns the size of table
    int size();
}