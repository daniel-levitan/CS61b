package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> ArrayComparator;

    //    creates a MaxArrayDeque with the given Comparator.
    public MaxArrayDeque(Comparator<T> c) {
        super();
        ArrayComparator = c;
    }

//    returns the maximum element in the deque as governed by the previously given Comparator.
//    If the MaxArrayDeque is empty, simply return null.
    public T max() {
        if (size() == 0) return null;

        int index = getFirstIndex();
        int count = 0;
        T max_value = items[index];

        while (count < size) {
            if (ArrayComparator.compare(items[index], max_value) > 0) {
                max_value = items[index];
            }

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
        return max_value;
    }

    public T max(Comparator<T> c){
        if (size() == 0) {
            return null;
        }

        int index = getFirstIndex();
        int count = 0;
        T max_value = items[index];

        while (count < size) {
            if (c.compare(items[index], max_value) > 0) {
                max_value = items[index];
            }

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
        return max_value;
    }

//    public static void main(String[] args) {
//        ArrayDeque<String> ad1 = new ArrayDeque<>();
//        ad1.addFirst("c");
//        ad1.addFirst("b");
//        ad1.addFirst("a");
//        ArrayDeque<Integer> ad2 = new MaxArrayDeque<>();
//        ad2.addFirst(3);
//        ad2.addFirst(8);
//        ad2.addFirst(2);
//        ad2.addFirst(1);
//        ad2.addFirst(4);
//        ad2.addFirst(1);
//
//        System.out.println(ad2.toList());
//        System.out.println(ad2);
//    }
}