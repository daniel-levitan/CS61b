package hashmap;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int capacity = 16; // 4 is better for testing
    int size = 0;
    double loadFactor = 0.75;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(capacity);
    }

    public MyHashMap(int initialCapacity) {
        capacity = initialCapacity;
        buckets = createTable(capacity);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = createTable(capacity);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // So far I don't know how to choose among several possibilities.
        // Let's start simple with a Linked List.
        return new LinkedList<>();
//        return null;
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] return_buckets;
        return_buckets = new Collection[tableSize];

        // Check if this works
//        for (Collection<Node> c : return_buckets)
//            c = createBucket();

        for (int i = 0; i < tableSize; i++)
            return_buckets[i] = createBucket();

        return return_buckets;
    }

    // TODO: Implement the methods of the Map61B Interface below
    private int getHash(K key, int buckets_capacity) {

        int hash = Math.floorMod(key.hashCode(), buckets_capacity);
        if (hash < 0)
            hash += buckets_capacity;
        return hash;
    }

    private void resize(int new_capacity) {
        Collection<Node>[] new_buckets;
        new_buckets = createTable(new_capacity);

        for (int i = 0; i < capacity; i++) {
            for (Node element : buckets[i])
                new_buckets[getHash(element.key, new_capacity)].add(createNode(element.key, element.value));
        }

        capacity = new_capacity;
        buckets = new_buckets;
    }

    // Your code won't compile until you do so!
    @Override
    public void put(K key, V value) {
        boolean found = false;

        for (Node element : buckets[getHash(key, capacity)]) {
            if (element.key.equals(key)) {
                element.value = value;
                found = true;
            }
        }
        if (!found) {
            buckets[getHash(key, capacity)].add(createNode(key, value));
            size++;
        }


        if (size * 1.0 / capacity > loadFactor)
            resize(capacity * 2);
    }

    @Override
    public V get(K key) {
        Node return_element = null;
        for (Node element : buckets[getHash(key, capacity)]) {
            if (element.key.equals(key))
                return_element = element;
        }
        if (return_element != null)
            return return_element.value;
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        for (Node element : buckets[getHash(key, capacity)]) {
            if (element.key.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        buckets = createTable(capacity);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }


    public static void main(String[] args) {
        MyHashMap<Bee, Integer> map = new MyHashMap<>();
        Map<Bee, Integer> ref = new HashMap<>();

        Bee b1 = new Bee(1);
        map.put(b1, 1);
        ref.put(b1, 1);

        Bee b2 = new Bee(2);
        map.put(b2, 2);
        ref.put(b2, 2);

        Bee b61 = new Bee(-61);
        map.put(b61, -61);
        ref.put(b61, -61);


        System.out.println("Finish");


    }

    private static class Bee {
        int b;

        Bee(int b) {
            this.b = b;
        }

        @Override
        public int hashCode() {
            return -61;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Bee other) {
                return Math.abs(b - other.b) < 61;
            }
            return false;
        }
    }
}
