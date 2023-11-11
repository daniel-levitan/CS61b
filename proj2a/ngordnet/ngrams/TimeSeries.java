package ngordnet.ngrams;

import java.sql.Time;
import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    // TODO: Add any necessary static/instance variables.
//    public TreeMap<Integer, Double> ts;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
//        this.ts = new TreeMap<>();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
//        this.ts = new TreeMap<>();
        for (int i = startYear; i < endYear; i++) put(i, ts.get(i));
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        List<Integer> result = new ArrayList<>();
        for (int k : keySet()) result.add(k);
        return result;
//        return null;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> result = new ArrayList<>();
        for (double v : values()) result.add(v);
        return result;
//        return null;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries new_ts = new TimeSeries();

        Set<Integer> entry_set = getCommonEntrySet(ts);

        for (int k : entry_set) {
            if (get(k) != null)
                new_ts.put(k, get(k));

            if (ts.get(k) != null) {
                if (new_ts.get(k) != null)
                    new_ts.put(k, ts.get(k) + new_ts.get(k));
                else
                    new_ts.put(k, ts.get(k));
            }
        }
        return new_ts;
//        return null;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        // timeSeries / ts
        Set<Integer> entry_set = getCommonEntrySet(ts);
        TimeSeries result = new TimeSeries();

        for (int k : entry_set) {
            if (ts.get(k) == null)
                throw new IllegalArgumentException("TS is missing.");

            if (get(k) != null)
                result.put(k, this.get(k) / ts.get(k));
        }
        return result;
//        return null;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    private Set<Integer> getCommonEntrySet(TimeSeries ts) {
        Set<Integer> entry_set = new TreeSet<>();
        for (int k : keySet()) {
            entry_set.add(k);
        }
        for (int k : ts.keySet()) {
            entry_set.add(k);
        }
        return entry_set;
    }
}
