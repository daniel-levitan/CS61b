package ngordnet.Graph;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class Graph {
    HashMap<Integer, List<String>> nodesIdToValues; // id maps words
    HashMap<String, List<Integer>> nodesValueToId; // word maps id

    HashMap<Integer, List<Integer>> graph;

    public Graph() {
        nodesIdToValues = new HashMap<>();
        nodesValueToId = new HashMap<>();
        graph = new HashMap<>();
    }

    public void createNode (int k, String v) {
        List<String> nodeList = new ArrayList<>();
        nodeList.addAll(Arrays.asList(v.split(" ")));   // Proposed by the IDE

        // Adding item to map Integer to Words
        nodesIdToValues.put(k, nodeList);

        // Adding item to map Word to Integer
        for (String word : nodeList) {
            if (!nodesValueToId.containsKey(word))
                nodesValueToId.put(word, new ArrayList<Integer>(List.of(k)));
            else
                nodesValueToId.get(word).add(k);
        }
    }

    public void addEdge (Integer src, Integer dest) {
        if (!graph.containsKey(src))
            graph.put(src, new ArrayList<Integer>(List.of(dest)));
        else
            graph.get(src).add(dest);
    }

    public List<Integer> getNodesIndices(String word) {
        List<Integer> index = nodesValueToId.get(word);
        return index;
    }

    public List<String> getNodeValues(Integer idx) {
        return nodesIdToValues.get(idx);
    }

    public List<Integer> getNeighbours(Integer idx) {
        return graph.get(idx);
    }


    private void preOrder(Integer x, Set<Integer> nodes) {
        nodes.add(x); //        System.out.print(x + " ");
        if (!graph.containsKey(x)) return;
        for (Integer c : graph.get(x)) {
            preOrder(c, nodes);
        }
    }

    public Set<Integer> findReachableVertices(Integer idx) {
        Set<Integer> nodes = new HashSet<>();
        preOrder(idx, nodes);
        return nodes;
    }


    public static void main(String[] args) {
        String hyponymFile = "./data/wordnet/hyponyms11.txt";
        Graph myGraph = new Graph();

        // READ
        In inHyponyms = new In(hyponymFile);
        String line;
        String fields[];

        // BUILD
        while (inHyponyms.hasNextLine()) {
            if (inHyponyms.isEmpty()) break;
            line = inHyponyms.readLine();
            fields = line.split(",");



//            for (String n : fields)
            for (int i = 1; i < fields.length; i++)
                myGraph.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
//                System.out.print(n + " ");
//            System.out.println();
//            myGraph.createNode(Integer.parseInt(fields[0]), fields[1]);
        }
        inHyponyms.close();

        System.out.println("Check if all is in");

    }

}
