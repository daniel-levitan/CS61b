package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    // TODO: Add any necessary static/instance variables.

    private class record_ {
        public String word;     // The first entry in each row is the word.
        public int year;    // The second entry is the year.
        public double appearances; // The third entry is the number of times that the word appeared in any book that year.
    };
    private double dummy;        // The fourth entry is the number of distinct sources that contain that word.
    private HashMap<String, TimeSeries> nGramMap = new HashMap<>();
    private TimeSeries totalWordsPerYear = new TimeSeries();;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        In inWords = new In(wordsFilename);
        In inCounts = new In(countsFilename);

        // Reading quantity of words per year
        while (inWords.hasNextLine()) {
            if (inWords.isEmpty()) break;
            record_ nextRecord = new record_();
            nextRecord.word = inWords.readString();
            nextRecord.year = inWords.readInt();
            nextRecord.appearances = inWords.readDouble();
            dummy = inWords.readDouble();

            if (nGramMap.containsKey(nextRecord.word)) {
                nGramMap.get(nextRecord.word).put(nextRecord.year, nextRecord.appearances);
            } else {
                TimeSeries wordTimeSeries = new TimeSeries();
                nGramMap.put(nextRecord.word, wordTimeSeries);
                nGramMap.get(nextRecord.word).put(nextRecord.year, nextRecord.appearances);
            }
        }

        // Reading total quantity words per year
        while (inCounts.hasNextLine()) {
            String line = inCounts.readLine();
            String[] arr = line.split(",", 0);
            totalWordsPerYear.put(Integer.valueOf(arr[0]), Double.valueOf(arr[1]));
        }
        // Closing files
        inWords.close();
        inCounts.close();

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            if (nGramMap.get(word).containsKey(i))
                r.put(i, nGramMap.get(word).get(i));
        }
        return r;
//        return null;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries wordMap = nGramMap.get(word);
        TimeSeries r = new TimeSeries();
        for (HashMap.Entry<Integer, Double> set : wordMap.entrySet())
            r.put(set.getKey(), set.getValue());
        return r;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        for (Map.Entry<Integer, Double> entry : totalWordsPerYear.entrySet())
            r.put(entry.getKey(), entry.getValue());
        return r;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            if (nGramMap.get(word).containsKey(i))
                r.put(i, nGramMap.get(word).get(i) / totalWordsPerYear.get(i));
        }
        return r;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        TimeSeries wordMap = nGramMap.get(word);

        for (Map.Entry<Integer, Double> entry : totalWordsPerYear.entrySet()) {
            if (wordMap.containsKey(entry.getKey())) {
                r.put(entry.getKey(), wordMap.get(entry.getKey()) / entry.getValue());
            }
        }
        return r;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        for (int i = startYear; i <= endYear; i++) {
            for (String s : words) {
                if (nGramMap.containsKey(s) && nGramMap.get(s).containsKey(i)) {
                    if (r.containsKey(i))
                        r.put(i, r.get(i) + nGramMap.get(s).get(i));
                    else
                        r.put(i, nGramMap.get(s).get(i));
                }
            }
            if (r.containsKey(i)) r.put(i, r.get(i) / totalWordsPerYear.get(i));
        }
        return r;

    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries r = new TimeSeries();
        for (String word : words) {
            TimeSeries wordMap = nGramMap.get(word);
            for (Map.Entry<Integer, Double> entry : totalWordsPerYear.entrySet()) {
                int i = entry.getKey();
                if (wordMap.containsKey(i)) {
                    if (r.containsKey(i))
                        r.put(i, r.get(i) + nGramMap.get(word).get(i));
                    else
                        r.put(i, nGramMap.get(word).get(i));
                    if (r.containsKey(i)) r.put(i, r.get(i) / totalWordsPerYear.get(i));
                }
            }
        }
        return r;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.

    public static void main(String[] args) {
//        String shortFile = "./data/ngrams/very_short.csv";
        String longFile = "./data/ngrams/top_14377_words.csv";
        String totalCountsFile = "./data/ngrams/total_counts.csv";

        NGramMap ngm = new NGramMap(longFile, totalCountsFile);

        // Checking times series for every word
//        for (Map.Entry<String, TimeSeries> entry : ngm.nGramMap.entrySet()) {
//            System.out.print(entry.getKey() + "\n");
//            for (Map.Entry<Integer, Double> wordEntry : entry.getValue().entrySet()) {
//                System.out.print(wordEntry.getKey() + ": " + wordEntry.getValue() + "\n");
//            }
//        }

        // Checking total words per year
//        TimeSeries ts = ngm.totalCountHistory();
//        for (Map.Entry<Integer, Double> entry : ts.entrySet())
//                System.out.print(entry.getKey() + ": " + entry.getValue() + "\n");


        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
//        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
//        System.out.println(fishCount.get(1865) + " vs 136497.0" + "\n");
//        System.out.println(fishCount.get(1922) + " vs 444924.0" + "\n");

//        TimeSeries totalCounts = ngm.totalCountHistory();
//        System.out.println(totalCounts.get(1865) + " vs 2563919231.0" + "\n");

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);

//        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);

//        List<String> fishAndDog = new ArrayList<>();
//        fishAndDog.add("fish");
//        fishAndDog.add("dog");
//        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

//        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;


        // My tests
//        List<String> coll = new ArrayList<>();
//        coll.add("airport");
//        coll.add("request");
//
//        TimeSeries r = new TimeSeries();
//        r = myMap.summedWeightHistory(coll, 2005, 2010);
//
//        for (Map.Entry<Integer, Double> entry : r.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

//        for (Map.Entry<Integer, Double> entry : ts.entrySet())
//            System.out.print(entry.getKey() + ": " + entry.getValue() + "\n");



    }
}
