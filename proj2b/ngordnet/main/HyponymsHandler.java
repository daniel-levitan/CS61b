package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.hyponym.WordNet;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {

    WordNet myHyp;

    public HyponymsHandler(WordNet myHyp) {
        this.myHyp = myHyp;
    }

    @Override
    public String handle(NgordnetQuery q) {
//        String synonymsFilename = "./data/wordnet/synsets16.txt";
//        String hyponymsFilename = "./data/wordnet/hyponyms16.txt";
//        WordNet myHyp = new WordNet(synonymsFilename, hyponymsFilename);
//        List<String> arrOfStrings = myHyp.getHyponyms(q.words().get(0));

        List<String> arrOfStrings = myHyp.getHyponyms(q.words());
        String response = "[";
        int size = arrOfStrings.size();
        for (int i = 0; i < size; i++) {
            response += arrOfStrings.get(i);
            if (i != size - 1) response += ", ";
        }
        response += "]";
        return response;
    }
}



