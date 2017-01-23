package kdr.pricing;

import java.util.Date;

/**
 * Mutable implementation of an Option.
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

    public enum PutCall { PUT, CALL }

    private String id;
    private String counterparty;
    private double strikePrice;
    private PutCall putCall;
    private Date expiryDate;

    // mutable fields
    private double impliedVol;
    private double mtm;
    private Date cobDate;


    public Option(String id, String counterparty, double strikePrice, PutCall putCall, Date expiryDate, double impliedVol) {
        if (expiryDate == null) throw new IllegalArgumentException("Must provide a non-null expiry date to " + getClass());
        this.id = id;
        this.counterparty = counterparty;
        this.strikePrice = strikePrice;
        this.putCall = putCall;
        this.expiryDate = new Date(expiryDate.getTime());
    }

}
