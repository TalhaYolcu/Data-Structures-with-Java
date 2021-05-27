

public class HashTableOpen<K,V> implements THashMap<K,V>{
    private Entry<K,V> [] table;   //hash table array
    private static final int START_CAPACITY=101;    //initial cap
    private double LOAD_THRESHOLD=0.75;          //maximum load factor
    private int numKeys;                //number of keys in the table excluding keys that were deleted
    private int numDeletes;     //number of deleted keys
    private final Entry<K,V> DELETED=new Entry<K,V>(null,null);       //a special object to indicate that an entry has been deleted

    public HashTableOpen() {table=new Entry[START_CAPACITY];}

    //Returns the index of the specified key if present in the table
    //constant time -> average case
    //worst case : theta(n)
    private int find(Object key) {
        int index=key.hashCode()%table.length;
        if(index<0) {
            index+=table.length;
        }
        while(table[index]!=null && !(key.equals(table[index].key))) {
            index++;
            if(index>=table.length) {
                index=0;
            }
        }
        return index;
    }
    //Doubles the cap,permanently removes deleted items
    private void rehash() {
        Entry<K,V>[] oldTable=table;
        table=new Entry[2*oldTable.length+1];

        numKeys=0;
        numDeletes=0;
        for(int i=0;i<oldTable.length;i++) {
            if((oldTable[i]!=null) && oldTable[i]!=DELETED) {
                put(oldTable[i].key,oldTable[i].value);
            }
        }
    }
    //same as find
    public V get(Object key) {
        int index=find(key);
        if(table[index]!=null) {
            return table[index].value;
        }
        else{
            return null;
        }
    }
    //constant time without rehash
    //linear time with rehash
    //amortized constant time
    //worst -> linear
    //amortized average -> constant
    public V put(K key,V value) {
        int index=find(key);
        if(table[index]==null) {
            table[index]=new Entry<K,V>(key, value);
            numKeys++;
            double loadfactor=(double)(numKeys+numDeletes)/table.length;
            if(loadfactor>LOAD_THRESHOLD) {
                rehash();
            }
            return null;
        }
        V oldVal=table[index].value;
        table[index].value=value;
        return oldVal;
    }
    //same as find method
    public V remove(Object key) {
        int index=find(key);
        if(table[index]==null) {
            return null;
        }
        V oldVal=table[index];
        table[index]=DELETED;
        numDeletes++;
        numKeys--;
        return oldVal;
    }

    public int size() {
        return table.length;
    }
    public boolean isEmpty() {
        return table.length==0;
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



