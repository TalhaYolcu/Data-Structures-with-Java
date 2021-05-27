public interface Map<K,V> {
    //returns the value associated with specified key
    V get(Object key);
    //returns true if this map contains no key value
    boolean isEmpty();
    //associates the specified value with key,returns previous value
    V put(K key,V value);
    //Remvoes the mapping for this key from this map if it is present
    V remove(Object key);
    //returns the number of key-value mappings
    int size();
}