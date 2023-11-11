package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    public class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node() {
            prev = null;
            next = null;
        }
    }

    private Node sentinel;
    private int size;


    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node new_node = new Node();
        new_node.item = x;

        new_node.next = sentinel.next;
        new_node.prev = sentinel;
        sentinel.next.prev = new_node;
        sentinel.next = new_node;

        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node new_node = new Node();
        new_node.item = x;

        new_node.prev = sentinel.prev;
        new_node.next = sentinel;
        sentinel.prev.next = new_node;
        sentinel.prev = new_node;

        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        // Print in order
        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }

        // Print in reverse order
//        Node p = sentinel.prev;
//        while (p != sentinel) {
//            returnList.add(p.item);
//            p = p.prev;
//        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        Node newFirst, oldFirst;

        // No elements on the list should return null
        if (size == 0) return null;

        // getting references for the first element and the new first element
        oldFirst = sentinel.next;
        newFirst = sentinel.next.next;

        // getting the value for the first element
        T returnValue = oldFirst.item;

        // changing the pointers for the new first element
        newFirst.prev = sentinel;
        sentinel.next = newFirst;

        // Freeing the pointers so this memory is reclaimed by the garbage collector
        oldFirst.prev = null;
        oldFirst.next = null;

        return returnValue;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        if (size == 0 || index > size)
            return null;

        Node p = sentinel.next;
        int count = 0;
        while (count < index) {
            count++;
            p = p.next;
        }
        return p.item;
    }

    private T getRec(int index, Node p) {
        if (index == 0)
            return p.item;
        return getRec(index - 1, p.next);
    }

    @Override
    public T getRecursive(int index) {
        return getRec(index, sentinel.next);
    }

    // According to the skeleton for this problem, we are request not to use casting, but
    // I don't know how we can avoid it since I need to compare elements or run through lists
    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque oas) {
            // check sets are of the same size
            if (oas.size != this.size) {
                return false;
            }

            // Second version
            Iterator it1 = this.iterator();
            Iterator it2 = ((LinkedListDeque<T>) o).iterator();

            while (it1.hasNext()) {
                if (it1.next() != it2.next())
                    return false;
            }
            return true;

            // First version
//            List<T> l1 = this.toList();
//            List<T> l2 = ((LinkedListDeque<T>) o).toList();
//            for (int i = 0; i < l1.size(); i++) {
//                if (l1.get(i) != l2.get(i))
//                    return false;
//            }
//            return true;
        }
        // o is not an ArraySet, so return false
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node curr;

        public LinkedListDequeIterator() {
            curr = sentinel.next;
        }

        public boolean hasNext() {
            return !(curr == null);
        }

        @Override
        public T next() {
            T returnItem = curr.item;
            if (curr.next != sentinel)
                curr = curr.next;
            else
                curr = null;
            return returnItem;
        }
    }

    @Override
    public String toString() {
        return toList().toString();
    }


    public static void main(String[] args) {
        LinkedListDeque<String> ll1 = new LinkedListDeque<>();
        LinkedListDeque<String> ll2 = new LinkedListDeque<>();

        ll1.addFirst("c");
        ll1.addFirst("b");
        ll1.addFirst("a");
        System.out.println(ll1);
        List<String> l = ll1.toList();
        l.toString();
        System.out.println(l);
//        ll2.addFirst("c");
//        ll2.addFirst("b");
//        ll2.addFirst("a");

//        System.out.println(ll1.equals(ll2));
//
//        ll2.removeFirst();
//        ll2.addFirst("z");
//
//        System.out.println(ll1.equals(ll2));
    }
}

