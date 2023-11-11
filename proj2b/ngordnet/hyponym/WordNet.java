package ngordnet.hyponym;

import com.google.common.collect.Sets;
import edu.princeton.cs.algs4.In;
import ngordnet.Graph.Graph;

import java.util.*;

public class WordNet {

    Graph hyponymGraph = new Graph();

    public WordNet(String synonymsFilename, String hyponymsFilename) {
        createNodeMaps(synonymsFilename);
        createEdgeGraph(hyponymsFilename);
    }

    private void createNodeMaps(String synonymsFilename) {
        // READ
        In inFile = new In(synonymsFilename);
        String fields[];

        // BUILD NODES MAPS
        while (inFile.hasNextLine()) {
            if (inFile.isEmpty()) break;
            fields = inFile.readLine().split(",");
            hyponymGraph.createNode(Integer.parseInt(fields[0]), fields[1]);
        }
        inFile.close();
    }

    private void createEdgeGraph(String HyponymsFilename) {
        // READ
        In inFile = new In(HyponymsFilename);
        String line;
        String fields[];

        // BUILD
        while (inFile.hasNextLine()) {
            if (inFile.isEmpty()) break;
            fields = inFile.readLine().split(",");

            for (int i = 1; i < fields.length; i++)
                hyponymGraph.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
        }
        inFile.close();

    }


    private void showMap() {
//        for (Integer k : synonymMap.keySet()) {
//            System.out.print(k + " ");
//            for (String word : synonymMap.get(k))
//                System.out.print(word + " ");
//            System.out.println();
//        }
    }

    private List<Integer> traverse(Integer idx) {
        List<Integer> arr = new ArrayList<>(hyponymGraph.findReachableVertices(idx));
        Collections.sort(arr);
        return arr;
    }

    public List<String> getHyponyms(List<String> words) {

        // Building the list of indices of hyponyms in sets
        List<Set<Integer>> hyponymsIdxsSet = new ArrayList<>();
        for (String w : words) {
            Set<Integer> s = new HashSet<>();
            for (Integer idx : hyponymGraph.getNodesIndices(w)){
                s.add(idx);
            }
            hyponymsIdxsSet.add(s);
        }

        // Building the hyponyms sets with traversals
        List<Set<Integer>> hyponymsSet = new ArrayList<>();
        for (Set<Integer> s : hyponymsIdxsSet) {
            Set<Integer> newSet = new HashSet<>();
            for (Integer i : s) {
                for (Integer e : traverse(i)) {
                    newSet.add(e);
                }
            }
            hyponymsSet.add(newSet);
        }

        // Checking the intersection among sets
        Set<Integer> resultSet = hyponymsSet.get(0);
        for (int i = 1; i < hyponymsSet.size(); i++) {
            resultSet = Sets.intersection(resultSet, hyponymsSet.get(i));
        }

        // Getting words for the final set
        Set<String> hyponymsWordsSet = new HashSet<>();
        for (Integer idx : resultSet) {
            for (String w : hyponymGraph.getNodeValues(idx)) {
                hyponymsWordsSet.add(w);
            }
        }
        List<String> returnArr = new ArrayList<>(hyponymsWordsSet);
        Collections.sort(returnArr);
        return returnArr;
    }

    public List<String> getHyponyms(String word) {
        List<Integer> wordIdxs = hyponymGraph.getNodesIndices(word);

        Set<Integer> hyponymsIdxsSet = new HashSet<>();

        for (Integer idx : wordIdxs) {
            for (Integer n : traverse(idx)) {
                hyponymsIdxsSet.add(n);
            }
        }

        Set<String> hyponymsWordsSet = new HashSet<>();

        for (Integer idx : hyponymsIdxsSet) {
            for (String w : hyponymGraph.getNodeValues(idx)) {
                hyponymsWordsSet.add(w);
            }
        }

        List<String> returnArr = new ArrayList<>(hyponymsWordsSet);
        Collections.sort(returnArr);
        return returnArr;
    }

    public static void main(String[] args) {
        String synonymsFilename = "./data/wordnet/synsets.txt";
        String hyponymFilename = "./data/wordnet/hyponyms.txt";

        WordNet myHyp = new WordNet(synonymsFilename, hyponymFilename);

        List<String> myList = new ArrayList<>();
        myList.add("car");
        myList.add("bug");
        System.out.println(myHyp.getHyponyms(myList));

        // Traversing car and bug
        System.out.println("Car:" + (myHyp.traverse(27405)));

        System.out.println("Bug:" + myHyp.traverse(45730));


//        Given a word (e.g. “change”), what nodes contain that word?
//        Example in synsets16.txt: change is in synsets 2 and 8
//        System.out.println(myHyp.hyponymGraph.getNodesIndices("change"));

//        Given an integer, what node goes with that index?
//        Necessary for processing hyponyms.txt.
//        For example in hyponyms16.txt, we know that the node with synset 8 points at synsets 9 and 10,
//        so we need to be able to find node 8 to get its adjacency list.
//        System.out.println(myHyp.hyponymGraph.getNeighbours(8));

//        Given a node, what words are in that node?
//        Example in synsets16.txt: synset 11 contains alteration, modification, and adjustment
//        System.out.println(myHyp.hyponymGraph.getNodeValues(11));

        // Finding reachable vertices, e.g. the vertices reachable from vertex #7 in hyponyms16.txt are 7, 8, 9, 10.
//        System.out.println(myHyp.hyponymGraph.findReachableVertices(7));

//        System.out.println(myHyp.traverse(7));

//        System.out.println(myHyp.getHyponyms("change"));

    }
}
