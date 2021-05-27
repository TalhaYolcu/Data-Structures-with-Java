public class HashSetOpen{
    private THashMap<K,V> setMap=new HashTableOpen<>();
    public boolean contains(Object key) {
        return setMap.get(key)!=null;
    }
    public boolean add(K key) {
        return setMap.put(key,key)==null;
    }
    public boolean remove(Object key) {
        return setMap.remove(key)!=null;
    }
}