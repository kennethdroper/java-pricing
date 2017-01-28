package kdr.pricing;

import java.util.Date;

/**
 * Mutable implementation of an Option.
 *
 * All prices assumed the same currency.  Currency not specified, BS doesn't need it :)
 *
 * Created by kennethroper on 18/01/2017.
 */
public class Option {
    public String getId() {
        return id;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public PutCall getPutCall() {
        return putCall;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    double getImpliedVol() {
        return impliedVol;
    }

    void setImpliedVol(double impliedVol) {
        this.impliedVol = impliedVol;
    }

    double getMtm() {
        return mtm;
    }

    void setMtm(double mtm) {
        this.mtm = mtm;
    }

    Date getCobDate() {
        return cobDate;
    }

    void setCobDate(Date cobDate) {
        this.cobDate = cobDate;
    }

    public double getUnderlyingPrice() {
        return underlyingPrice;
    }

    public enum PutCall { PUT, CALL }

    private String id;
    private String counterparty;
    private double strikePrice;
    private PutCall putCall;
    private Date expiryDate;
    private double underlyingPrice;

    // mutable fields
    private double impliedVol;
    private double mtm;

    /** The close-of-business date which relates to all prices */
    private Date cobDate;


    public Option(String id, String counterparty, PutCall putCall, Date expiryDate,
                  double impliedVol, double strikePrice, double underlyingPrice, Date cobDate) {

        // Validate inputs
        if (expiryDate == null) throw new IllegalArgumentException("Must provide a non-null expiry date to " + getClass());
        if (cobDate == null) throw new IllegalArgumentException("Must provide a non-null cobDate to " + getClass());

        // Assign
        this.id = id;
        this.counterparty = counterparty;
        this.strikePrice = strikePrice;
        this.putCall = putCall;
        this.expiryDate = new Date(expiryDate.getTime());
        this.impliedVol= impliedVol;
        this.strikePrice = strikePrice;
        this.underlyingPrice = underlyingPrice;
        this.cobDate = new Date (cobDate.getTime());
    }

    /**
     * @return the difference between the expiryDate and the cobDate as a year fraction.  All days
     * of the year are considered working days to calculate this fraction.  A negative fraction indicates
     * this option has expired.
     */
    public double getTimeToExpiry() {
        long cobInMillis = cobDate.getTime();
        long expiryInMillis = expiryDate.getTime();

        long difference = expiryInMillis - cobInMillis;

        return convertMillisToYearFraction(difference);
    }

    /**
     * Basic implementation of year fractions.  Does not take into account leap years.
     *
     * TODO: separate into a utility class and work directly works with date functions.
     *
     * @param millis
     * @return
     */
    private float convertMillisToYearFraction(long millis){
        float yearFraction = (float) millis / 1000f / 60f / 60f / 24f / 365f;
        float secs = millis / 1000f;
        float mins = secs / 60f;
        float hours = mins / 60f;
        float days = mins / 24f;
        float years = days / 365f;
        return yearFraction;
    }

}
