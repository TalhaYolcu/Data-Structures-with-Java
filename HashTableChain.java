import java.util.LinkedList;

public class HashTableChain<K,V> implements THashMap<K,V> {
    private LinkedList<Entry<K,V>>[] table;
    private int numKeys;        //number of keys
    private static final int CAPACITY=101;  //size of table
    private static final double LOAD_THRESHOLD=3.0;        //maximum load factor

    public HashTableChain() {
        table=new LinkedList[CAPACITY];
    }

    //O(n)
    //average -> constant n/table.length -> factor
    public V get(Object key) {
        int index=key.hashCode() % table.length;
        if(index<0) {
            index+=table.length;
        }
        if(table[index]==null) {
            return null;
        }
        for(Entry<K,V> nextItem:table[index]) {
            if(nextItem.key.equals(key)) {
                return nextItem.value;
            }
        }
        return null;
    }

    //Tw-> O(n)
    //Tb-> constant
    //Ta-> constant time -> amortized
    public V put(K key,V value) {
        int index=key.hashCode()%table.length;
        if(index<0) {
            index+=table.length;
        }
        if(table[index]==null) {
            table[index]=new LinkedList<Entry<K,V>>();
        }
        for(Entry<K,V> nextItem:table[index]) {
            if(nextItem.key.equals(key)) {
                V oldVal=nextItem.value;
                nextItem.setValue(value);
                return oldVal;
            }
        }
        table[index].addFirst(new Entry<K,V>(key,value));
        numKeys++;
        if(numKeys>(LOAD_THRESHOLD*table.length)) {
            rehash();
        }
        return null;
    }
    //Tw -> linear
    //Tb -> constant, Ta->constant ,amortized
    public V remove(Object key) {
        int index=key.hashCode()%table.length;
        if(index<0) {
            index+=table.length;
        }
        if(table[index]==null) {
            return null;
        }
        for(Entry<K,V> nextItem:table[index]) {
            if(nextItem.key.equals(key)) {
                V oldval=nextItem.value;
                table[index].remove(nextItem);
                numKeys--;
                return oldval;
            }
        }

        /*
        Iterator<Entry<K,V>> iter=table[index].iterator();
        while(iter.hasNext()) {
            Entry <K,V> nextItem=iter.next();
            if(nextItem.key.equals(key)) {
                V returnValue=nextItem.value;
                iter.remove();
                return returnValue;
            }
        }
        */
        return null;
    }
    public int size() {
        return numKeys;
    }
    public boolean isEmpty() {
        return numKeys == 0;
    }
    
    public void rehash() {
        LinkedList<Entry<K,V >>[] oldtable=table;
        table=new LinkedList[2*oldtable.length+1];

        numKeys=0;
        for(int i=0;i<oldtable.length;i++) {
            if(oldtable[i]!=null) {
                for(Entry<K,V> nextEntry:oldTable[i]) {
                    put(nextEntry.key,nextEntry.value);
                }
            }
        }
    }
    private static class Entry<K,V> {
        private K key;
        private V value;

        public Entry(K key,V value) {
            this.key=key;
            this.value=value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public setValue(V val) {
            V oldVal=value;
            value=val;
            return oldVal;
        }
    }
}