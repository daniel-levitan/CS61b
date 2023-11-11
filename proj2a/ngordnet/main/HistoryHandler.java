package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;


public class HistoryHandler extends NgordnetQueryHandler {
    public NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        ngm = map;
    }


    @Override
    public String handle(NgordnetQuery q) {
//        System.out.println("Got query that looks like:");
//        System.out.println("Words: " + q.words());
//        System.out.println("Start Year: " + q.startYear());
//        System.out.println("End Year: " + q.endYear());

//        System.out.println("But I'm totally ignoring that and just plotting a parabola\n" +
//                        "and a sine wave, because your job will be to figure out how to\n" +
//                        "actually use the query data.");

//        TimeSeries parabola = new TimeSeries();
//        for (int i = 0; i < 100; i += 1) {
//            parabola.put(i, (i - 50.0) * (i - 50.0) + 3);
//        }
//
//        TimeSeries sinWave = new TimeSeries();
//        for (int i = 0; i < 100; i += 1) {
//            sinWave.put(i, 1000 + 500 * Math.sin(i/100.0*2*Math.PI));
//        }

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

//        labels.add("parabola");
//        labels.add("sine wave");

//        lts.add(parabola);
//        lts.add(sinWave);

        for (String word : q.words()) {
            labels.add(word);
            lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(q.words(), lts);
        String s = Plotter.encodeChartAsString(chart);
        System.out.println(s);

        // you can also do this to display locally:
         Plotter.displayChart(chart);

        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}
