package kdr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility functions for manipulating date-based datatypes.
 *
 * This class is threadsafe.
 *
 * Created by kennethroper on 28/01/2017.
 */
public final class DateUtility {

    /**
     * Convenient constant for 24 hours expressed as a year fraction.
     */
    public final static float YEAR_FRACTION_FOR_1_DAY = 1.0f / 365f;


    /** Prevent instantiation, this is a stateless singleton. */
    private DateUtility () {}

    /**
     * Return the time between the startDate and the endDate as a year fraction.
     *
     * This is a basic implementation which does not account for day count conventions or leap years -
     * all years are assumed to have exactly 365 days.  The year fraction will also take into account
     * the time of day, to millisecond precision.
     *
     * @param startDate the earlier date
     * @param endDate the later date
     * @throws NullPointerException if either of the supplied dates are null
     * @return endDate - startDate as a year fraction
     */
    public static double getYearFraction(Date startDate, Date endDate) {

        long cobInMillis = startDate.getTime();
        long expiryInMillis = endDate.getTime();

        long difference = expiryInMillis - cobInMillis;

        return convertMillisToYearFraction(difference);
    }

    /**
     * Basic implementation of year fractions.  Does not take into account leap years.
     *
     * @param millis a milliseconds value to convert to a year fraction
     * @return the year fraction
     */
    private static float convertMillisToYearFraction(long millis){
        float yearFraction = (float) millis / 1000f / 60f / 60f / 24f / 365f;
        float secs = millis / 1000f;
        float mins = secs / 60f;
        float hours = mins / 60f;
        float days = mins / 24f;
        float years = days / 365f;
        return yearFraction;
    }

    /**
     * Convert a string into a date.  Only one format is supported.
     * @param yyyyMMDD
     * @return the Date representation of the string
     * @throws IllegalArgumentException if the given string cannot be parsed in yyyyMMDD format.
     */
    public static Date parseYYYYMMDD(String yyyyMMDD) {
        DateFormat sd= new SimpleDateFormat("yyyyMMdd");
        Date result= null;
        try {
            result= sd.parse(yyyyMMDD);
        }
        catch (ParseException pe)
        {
            // Deliberate change of style to use unchecked exception
            throw new IllegalArgumentException(pe);
        }
        return result;
    }

}
