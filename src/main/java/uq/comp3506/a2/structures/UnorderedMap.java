// @edu:student-assignment

package uq.comp3506.a2.structures;

import java.util.ArrayList;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 * <p>
 * NOTE: You should go and carefully read the documentation provided in the
 * MapInterface.java file - this explains some of the required functionality.
 */
public class UnorderedMap<K, V> implements MapInterface<K, V> {


    /**
     * you will need to put some member variables here to track your
     * data, size, capacity, etc...
     */
    private ArrayList<Entry<K, V>>[] table;
    private int size;
    private int capacity;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Constructs an empty UnorderedMap
     */
    @SuppressWarnings("unchecked")
    public UnorderedMap() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.table = (ArrayList<Entry<K, V>>[]) new ArrayList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new ArrayList<>();
        }
    }

    /**
     * returns the size of the structure in terms of pairs
     * @return the number of kv pairs stored
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * helper to indicate if the structure is empty or not
     * @return true if the map contains no key-value pairs, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the map. That means, after calling clear(),
     * the return of size() should be 0, and the data structure should appear
     * to be "empty".
     */
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            table[i].clear();
        }
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value
     * is replaced by the specified value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the payload data value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no such key
     */
    @Override
    public V put(K key, V value) {
        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }
        
        int index = getIndex(key);
        ArrayList<Entry<K, V>> bucket = table[index];
        
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entry.setValue(value);
                return oldValue;
            }
        }
        
        bucket.add(new Entry<>(key, value));
        size++;
        return null;
    }

    /**
     * Looks up the specified key in this map, returning its associated value
     * if such key exists.
     *
     * @param key the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    @Override
    public V get(K key) {
        int index = getIndex(key);
        ArrayList<Entry<K, V>> bucket = table[index];
        
        for (Entry<K, V> entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        
        return null;
    }

    /**
     * Looks up the specified key in this map, and removes the key-value pair
     * if the key exists.
     *
     * @param key the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    @Override
    public V remove(K key) {
        int index = getIndex(key);
        ArrayList<Entry<K, V>> bucket = table[index];
        
        for (int i = 0; i < bucket.size(); i++) {
            Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                V value = entry.getValue();
                bucket.remove(i);
                size--;
                return value;
            }
        }
        
        return null;
    }
    
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        ArrayList<Entry<K, V>>[] oldTable = table;
        
        table = (ArrayList<Entry<K, V>>[]) new ArrayList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new ArrayList<>();
        }
        
        capacity = newCapacity;
        size = 0;
        
        for (ArrayList<Entry<K, V>> bucket : oldTable) {
            for (Entry<K, V> entry : bucket) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

}
