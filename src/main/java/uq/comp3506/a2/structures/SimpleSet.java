// @edu:student-assignment

package uq.comp3506.a2.structures;

/**
 * A simple Set implementation based on UnorderedMap.
 * This is used to replace HashSet which is not allowed.
 */
public class SimpleSet<E> {
    
    private UnorderedMap<E, Boolean> map;
    
    /**
     * Constructs an empty SimpleSet
     */
    public SimpleSet() {
        this.map = new UnorderedMap<>();
    }
    
    /**
     * Adds an element to the set
     * @param element the element to add
     * @return true if the element was added, false if it was already present
     */
    public boolean add(E element) {
        Boolean oldValue = map.put(element, Boolean.TRUE);
        return oldValue == null;
    }
    
    /**
     * Checks if the set contains an element
     * @param element the element to check
     * @return true if the element is in the set, false otherwise
     */
    public boolean contains(E element) {
        return map.get(element) != null;
    }
    
    /**
     * Removes an element from the set
     * @param element the element to remove
     * @return true if the element was removed, false if it wasn't present
     */
    public boolean remove(E element) {
        Boolean oldValue = map.remove(element);
        return oldValue != null;
    }
    
    /**
     * Returns the number of elements in the set
     * @return the size of the set
     */
    public int size() {
        return map.size();
    }
    
    /**
     * Checks if the set is empty
     * @return true if the set is empty, false otherwise
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    /**
     * Clears all elements from the set
     */
    public void clear() {
        map.clear();
    }
    
    /**
     * Adds all elements from another collection to this set
     * @param collection the collection to add from
     */
    public void addAll(Iterable<E> collection) {
        for (E element : collection) {
            add(element);
        }
    }
}

