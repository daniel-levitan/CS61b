import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> map = new TreeMap<>();
        for (int i = 1; i < 27; i++)
            map.put((char) (96 + i), i);

        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (Integer elem : nums)
            map.put(elem, (int) Math.pow(elem, 2));

        return map;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> map = new TreeMap<>();
        for (String word : words)
            if (map.get(word) == null)
                map.put(word, 1);
            else
                map.put(word, map.get(word) + 1);
        return map;

    }

    public static void main(String[] args) {
        // Ex1
        Map<Character, Integer> map = letterToNum();
        for (int i = 0; i < 26; i++)
            System.out.println((char) (97 +
            i) + " " + map.get((char) (97 + i)) );

        // Ex2
        List<Integer> lst1 = List.of(1, 2, 3);
        Map<Integer, Integer> map2 = squares(lst1);
        for (Integer key : map2.keySet())
           System.out.println(key + " " + map2.get(key));



    }
}
