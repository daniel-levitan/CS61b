import java.util.ArrayList;
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

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(3);
        lld.addFirst(2);
        lld.addFirst(1);

//        lld.addLast(4);
//        lld.addLast(5);
//        lld.addLast(6);


        lld.removeFirst();
        lld.removeFirst();
        lld.removeFirst();
        List<Integer> returnList = lld.toList();

        System.out.println(returnList);
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


}
