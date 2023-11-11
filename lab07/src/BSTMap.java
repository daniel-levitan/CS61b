import java.util.Iterator;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
//import edu.princeton.cs.algs4.BST;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    private class BSTNode {
        private K key;
        private V value;
        private int size;

        private BSTNode left;
        private BSTNode right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            size = 1;
        }

    }
    private BSTNode root;

    public BSTMap(){
        root = null;
    }

    @Override
    public void put(K key, V value) {
        root = insert(root, key, value);
    }

    private BSTNode insert(BSTNode node, K key, V value) {
        if (node == null)
            return new BSTNode(key, value);

        int cmp = key.compareTo(node.key);
        if (cmp == 0)
            node.value = value;
        else if (cmp < 0)
            node.left = insert(node.left, key, value);
        else
            node.right = insert(node.right, key, value);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public V get(K key) {
        BSTNode node = search(root, key);
        if (node == null) return null;
        return node.value;
    }

    private BSTNode search(BSTNode node, K key) {
        if (node == null)
            return null;

        int cmp =  key.compareTo(node.key);
        if (cmp == 0)
            return node;
        else if (cmp < 0)
            return search(node.left, key);
        else
            return search(node.right, key);
    }

    @Override
    public boolean containsKey(K key) {
        BSTNode node = search(root, key);
        if (node != null)
            return true;
        return false;
    }

    @Override
    public int size() {
        return size(root); //.size;
    }

    public int size(BSTNode node) {
        if (node == null) return 0;
        return node.size;
    }

    @Override
    public void clear() {
        root = null;
    }

    public void printInOrder() {
        printInOrder(this.root);
        System.out.println();
    }

    private void printInOrder(BSTNode root) {
        if (root == null) return;

        if (root.left != null)
            printInOrder(root.left);
        System.out.print(root.value + " ");
        if (root.right != null)
            printInOrder(root.right);
    }

    // I don't get this
//    public int compareTo(BSTMap<K,V> other) {
//        return this.root.key.compareTo(other.root.key);
//    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Method not supported.");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Method not supported.");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Method not supported.");
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> b = new BSTMap<>();
        int i = 0;
//        for (int i = 0; i < 455; i++) {
        b.put("hi" + i, 1+i);
        System.out.print(b.get("hi" + i));
            //make sure put is working via containsKey and get
//        System.out.print(b.get("hi" + i) + " " + (1 + i));
//        System.out.print(b.containsKey("hi" + i));
//            assertThat(b.get("hi" + i)).isEqualTo(1 + i);
//            assertThat(b.containsKey("hi" + i)).isTrue();
//        }
//        BSTMap<Integer, Integer> myMap = new BSTMap<>();
//
//        myMap.put(6, 6);
//        System.out.println(myMap.root.size);
//        myMap.put(4, 4);
//        System.out.println(myMap.root.size);
//        myMap.put(8, 8);
//        System.out.println(myMap.root.size);
//
//        myMap.put(3, 3);
//        myMap.put(5, 5);
//        System.out.println(myMap.root.size);
//
//        myMap.put(7, 7);
//        myMap.put(9, 9);
//        System.out.println(myMap.root.size);
//
//        myMap.clear();
//        System.out.println(myMap.size());
//        myMap.printInOrder();

//        System.out.println(myMap.get(6));
//        System.out.println(myMap.get(3));
//        System.out.println(myMap.get(27));
    }

}
