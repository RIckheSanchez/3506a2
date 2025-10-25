// @edu:student-assignment

package uq.comp3506.a2.structures;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 */
public class Entry<K, V> {

    /** The key is used for comparisons (in heaps, maps, etc)*/
    private K key;

    /** The value is the payload data object*/
    private V value;

    /** The basic constructor taking a key and value*/
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /** Gets the key, who would have thought*/
    public K getKey() {
        return this.key;
    }

    /** Get the value*/
    public V getValue() {
        return this.value;
    }

    /** Set (update) the value*/
    public void setValue(V value) {
        this.value = value;
    }

    // You may add more helper functions here, if you wish
}
