// @edu:student-assignment

package uq.comp3506.a2.structures;

/**
 * Supplied by the COMP3506/7505 teaching team, Semester 2, 2025.
 * An abstract data type representing the ``map'' operations to represent
 * a collection of K, V pairs, indexed by the key K.
 * Note that this is a similar interface to java.util.Map but is indeed
 * different in some ways (a simplified version).
 */
public interface MapInterface<K, V> {

    /**
     * size is the logical size of the structure; that is, how many valid
     * elements are stored within.
     *
     * @return the number of elements stored inside the given structure
     */
    int size();

    /**
     * a simple helper function that indicates if the structure is empty
     * @return true if there are no key-value pairs, false otherwise
     */
    boolean isEmpty();

    /**
     * Clears all elements from the map. That means, after calling clear(),
     * the return of size() should be 0, and the data structure should appear
     * to be "empty"
     */
    void clear();

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value
     * is replaced by the specified value, and the old value is returned.
     * If there is no "old" value associated, null is returned. null will
     * never be considered as a valid value.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the payload data value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no such key
     */
    V put(K key, V value);
 
    /**
     * Looks up the specified key in this map, returning its associated value
     * if such key exists.
     *
     * @param key   the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    V get(K key);
    
    /**
     * Looks up the specified key in this map, and remove the K,V pair if the
     * key exists. 
     *
     * @param key   the key with which the specified value is to be associated
     * @return the value associated with key, or null if there was no such key
     */
    V remove(K key); 


}
