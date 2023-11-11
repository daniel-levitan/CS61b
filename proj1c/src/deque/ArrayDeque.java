package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    public T[] items;
    public int size;
    public int nextFirst;
    public int nextLast;
    public int capacity;


    public ArrayDeque() {
        size = 0;
        capacity = 4;
        nextFirst = capacity / 2 - 1;
        nextLast = capacity / 2;
        items = (T[])  new Object[capacity]; // That doesn't work: items = new T[capacity] ;
    }

    public ArrayDeque(int c) {
        size = 0;
        capacity = c;
        nextFirst = capacity / 2 - 1;
        nextLast = capacity / 2;
        items = (T[])  new Object[capacity]; // That doesn't work: items = new T[capacity] ;
    }

    /** Resize to bigger or smaller capacity */
    private void resize(int new_capacity) {
        T[] new_items = (T[])  new Object[new_capacity];

        // Increasing size
        int old_index = getFirstIndex();
        int new_index = new_capacity / 2;
        int count = 0;

        nextFirst = new_index - 1;
        while (count < size) {
            new_items[new_index] = items[old_index];
            // increment old index
            old_index++;
            if (old_index == capacity) old_index = 0;
            // increment new index
            new_index++;
            if (new_index == new_capacity) new_index = 0;
            count++;
        }

//        nextLast = 0; // This will always happen since I will start filling up from the middle
        nextLast = new_index++;
        if (new_index == new_capacity) new_index = 0;
        capacity = new_capacity;
        items = new_items;

    }

    public int getFirstIndex() {
        if (size == 0)
            return -1;

        if (nextFirst  >= capacity - 1)
            return 0;
        return nextFirst + 1;
    }

    // Updating the index of the next first position
    private void updateNextFirst() {
        if (nextFirst - 1 < 0)
            nextFirst = capacity - 1;
        else
            nextFirst--;
    }

    // Updating the index of the next last position
    private void updateNextLast() {
        if (nextLast > capacity - 1 - 1)
            nextLast = 0;
        else
            nextLast++;
    }

    @Override
    public void addFirst(T x) {
        if (size == capacity)
            resize(capacity * 2);

        items[nextFirst] = x;
        size++;
        updateNextFirst();

    }

    @Override
    public void addLast(T x) {
        if (size == capacity)
            resize(capacity * 2);

        items[nextLast] = x;
        size++;
        updateNextLast();
    }

    @Override
    public List<T> toList() {
        List<T> result_list = new ArrayList<>();

        if (size == 0) return result_list;

        int index;
        int count = 0;

        index = getFirstIndex();

        while (count < size) {
            result_list.add(items[index]);
            if (nextFirst + 1 <= nextLast - 1)
                index++;
            else {
                if (index < capacity - 1)
                    index++;
                else
                    index = 0;
            }
            count++;
        }
        return result_list;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void updatePrevFirst() {
        if (nextFirst == capacity - 1)
            nextFirst = 0;
        else
            nextFirst++;
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            updatePrevFirst();
            T element = items[nextFirst];
            items[nextFirst] = null; // cleaning the first spot
            size--;
            if (size < capacity / 4)
                resize(capacity / 4);
            return element;
        }

        return null;
    }

    private void updatePrevLast() {
        if (nextLast == 0)
            nextLast = capacity - 1;
        else
            nextLast--;
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            updatePrevLast();
            T element = items[nextLast];
            items[nextLast] = null; // cleaning the first spot
            size--;
            if (size < capacity / 4)
                resize(capacity / 4);
            return element;
        }


        return null;
    }

    @Override
    public T get(int index) {
        if (index < size) {
            if (index + nextFirst + 1 < capacity) {
                return items[index + nextFirst + 1];
            } else {
                index = index - (capacity - nextFirst) + 1;
                return items[index];
            }

        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int index;
        int count;

        public ArrayDequeIterator() {
            count = 0;
            index = nextFirst + 1;
        }

        public boolean hasNext() {
            return count < size();
        }

        public T next() {
            T returnItem = items[index];
            if (nextFirst + 1 <= nextLast - 1)
                index++;
            else {
                if (index < capacity - 1)
                    index++;
                else
                    index = 0;
            }
            count++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque oas) {
            // check sets are of the same size
            if (oas.size != this.size) {
                return false;
            }

            // Second version
            Iterator it1 = this.iterator();
            Iterator it2 = ((ArrayDeque<T>) o).iterator();

            while (it1.hasNext()) {
                if (it1.next() != it2.next())
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return toList().toString();
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> my_deque = new ArrayDeque<>();
        my_deque.addLast(5);
        my_deque.addLast(4);
        my_deque.addFirst(3);
        my_deque.addFirst(2);
        my_deque.addFirst(1);

        System.out.println(my_deque.toList());

        for (int i = 0; i < my_deque.size(); i++) {
            System.out.print(my_deque.get(i) + " ");
        }




//        Iterator it = my_deque.iterator();
//
//        while (it.hasNext()) {
//            System.out.print(it.next());
//        }
//        System.out.println();

//        ArrayDeque<String> ad1 = new ArrayDeque<>();
//
//        ad1.addLast("e");
//        ad1.addLast("d");
//
//        ad1.addFirst("c");
//        ad1.addFirst("b");
//        ad1.addFirst("a");
//
//
//        Iterator it = ad1.iterator();
//
//        System.out.println("Iterator:");
//        System.out.println("Has next: " + it.hasNext());
//        while (it.hasNext())
//            System.out.println(it.next());

//        System.out.println("To list:");
//        System.out.println(ad1.toList());
    }
}
