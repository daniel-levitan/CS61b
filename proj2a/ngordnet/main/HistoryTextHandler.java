package ngordnet.main;

import net.sf.saxon.regex.History;
import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.List;
import java.util.Map;

public class HistoryTextHandler extends NgordnetQueryHandler {
    public NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {

        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

//        response += "Words: " + q.words() + "\n";
//        response += "Start Year: " + q.startYear() + "\n";
//        response += "End Year: " + q.endYear() + "\n";

        String response = "";
        int i = 0;
        for (String s : q.words()) {
            response += s + ": ";
            response += ngm.weightHistory(s, q.startYear(), q.endYear()).toString();

//            response += s + ": {";
//            TimeSeries ts = ngm.weightHistory(s, q.startYear(), q.endYear());
//            int count = 0;
//            for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
//                response += entry.getKey().toString() + "=" + entry.getValue().toString();
//                if (count != ts.size() - 1) response += ", ";
//                count++;
//            }
//            response += "}";
            if (i != q.words().size()  - 1) response += "\n";
            i++;
        }
        return response;
    }
}
