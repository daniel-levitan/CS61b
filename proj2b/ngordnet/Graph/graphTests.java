package ngordnet.Graph;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the Graph class.
 *  @author Daniel Levitan
 */
public class graphTests {

    @Test
    public void createNodesSmall() {
        String synsetFile = "./data/wordnet/synsets11.txt";
        Graph myGraph = new Graph();

        // READ
        In inSynonymns = new In(synsetFile);
        String line;
        String fields[];

        // BUILD NODES MAPS
        while (inSynonymns.hasNextLine()) {
            if (inSynonymns.isEmpty()) break;
            fields = inSynonymns.readLine().split(",");
            myGraph.createNode(Integer.parseInt(fields[0]), fields[1]);
        }
        inSynonymns.close();

        // Test Integer to Words
        assertThat(myGraph.getNodeValues(0)).isEqualTo(Arrays.asList("action"));
        assertThat(myGraph.getNodeValues(4)).isEqualTo(Arrays.asList("jump", "parachuting"));

        // Test Word to Integers
        assertThat(myGraph.getNodesIndices("augmentation")).isEqualTo(Arrays.asList(7));
        assertThat(myGraph.getNodesIndices("jump")).isEqualTo(Arrays.asList(4, 6));
    }


    @Test
    public void createGraphSmall() {
        String hyponymFile = "./data/wordnet/hyponyms11.txt";
        Graph myGraph = new Graph();

        // READ
        In inHyponyms = new In(hyponymFile);
        String fields[];

        // BUILD
        while (inHyponyms.hasNextLine()) {
            if (inHyponyms.isEmpty()) break;
            fields = inHyponyms.readLine().split(",");

            for (int i = 1; i < fields.length; i++)
                myGraph.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
        }
        inHyponyms.close();

        // Test Integer to Words
//        assertThat(myGraph.getNodeValues(0)).isEqualTo(Arrays.asList("action"));
//        assertThat(myGraph.getNodeValues(4)).isEqualTo(Arrays.asList("jump", "parachuting"));

        // Test Word to Integers
//        assertThat(myGraph.getNodesIndices("augmentation")).isEqualTo(Arrays.asList(7));
//        assertThat(myGraph.getNodesIndices("jump")).isEqualTo(Arrays.asList(4, 6));
    }

}
