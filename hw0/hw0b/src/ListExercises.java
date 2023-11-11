import java.util.List;
import java.util.ArrayList;

public class ListExercises {


    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        Integer total = 0;
        for (int i = 0; i < L.size(); i++)
            total += L.get(i);
        return total;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            if (L.get(i) % 2 == 0)
                result.add(L.get(i));
        }
        return result;
    }


    // Nao consegui botar isso aqui dentro de uma classe.
    public static Boolean IsPart(Integer item, List<Integer> L) {
        for (int i = 0; i < L.size(); i++)
            if (item == L.get(i))
                return true;
        return false;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < L1.size(); i++)
            if (IsPart(L1.get(i), L2))
                result.add(L1.get(i));
        return result;
    }


    public static int countOccurrenceInWord(String word, char c) {
        int total = 0;
        for (int i = 0; i < word.length(); i++)
            if (word.charAt(i) == c)
                total++;
        return total;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int total = 0;
        for (int i = 0; i < words.size(); i++)
            total += countOccurrenceInWord(words.get(i), c);
        return total;
    }

//    public static void main(String[] args) {
//        List<Integer> lst1 = List.of(1, 2, 3, 4);
//        System.out.println(sum(lst1));
//        System.out.println(evens(lst1));
//
//    }
}

