package ngordnet.main;

import ngordnet.browser.NgordnetServer;
//import ngordnet.ngrams.NGramMap;
import ngordnet.hyponym.WordNet;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        String wordFile = "./data/ngrams/top_49887_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";
        WordNet myHyp = new WordNet(synsetFile, hyponymFile);

//        NGramMap ngm = new NGramMap(wordFile, countFile);
        // Here I will call the wordnet

        hns.startUp();
//        hns.register("history", new HistoryHandler(ngm));
//        hns.register("historytext", new HistoryTextHandler(ngm));
//        hns.register("history", new DummyHistoryHandler());
//        hns.register("historytext", new DummyHistoryTextHandler());
        hns.register("hyponyms", new HyponymsHandler(myHyp));
    }
}
