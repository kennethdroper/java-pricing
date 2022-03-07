package kdr.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import kdr.pricing.Option;

import java.util.Objects;

/**
 * Inputs to the Black-Scholes Option Pricing Algorithm.
 *
 * Immutable implementation, threadsafe.
 */
public final class PricingParams {

    private final Option.PutCall putCall;
    private final double impliedVolatility;
    private final float strikePrice;
    private final float underlyingPrice;
    private final float yearsToMaturity;
    private final double riskFreeRate;

    /**
     * Need JsonProperty specified to remain immutable and be deserialisable by Jackson Databind
     * @param putCall
     * @param impliedVolatility
     * @param strikePrice
     * @param underlyingPrice
     * @param yearsToMaturity
     * @param riskFreeRate
     */
    public PricingParams(@JsonProperty("putCall") Option.PutCall putCall,
                         @JsonProperty("impliedVolatility") double impliedVolatility,
                         @JsonProperty("strikePrice") float strikePrice,
                         @JsonProperty("underlyingPrice") float underlyingPrice,
                         @JsonProperty("yearsToMaturity") float yearsToMaturity,
                         @JsonProperty("riskFreeRate") double riskFreeRate) {
        this.putCall = putCall;
        this.impliedVolatility = impliedVolatility;
        this.strikePrice = strikePrice;
        this.underlyingPrice = underlyingPrice;
        this.yearsToMaturity = yearsToMaturity;
        this.riskFreeRate = riskFreeRate;
    }

    public Option.PutCall getPutCall() {
        return putCall;
    }

    public double getImpliedVolatility() {
        return impliedVolatility;
    }

    public float getStrikePrice() {
        return strikePrice;
    }

    public float getUnderlyingPrice() {
        return underlyingPrice;
    }

    public float getYearsToMaturity() {
        return yearsToMaturity;
    }

    public double getRiskFreeRate() {
        return riskFreeRate;
    }

    @Override
    public String toString() {
        return "PricingParams{" +
                "putCall=" + putCall +
                ", impliedVolatility=" + impliedVolatility +
                ", strikePrice=" + strikePrice +
                ", underlyingPrice=" + underlyingPrice +
                ", yearsToMaturity=" + yearsToMaturity +
                ", riskFreeRate=" + riskFreeRate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PricingParams that = (PricingParams) o;
        return Double.compare(that.impliedVolatility, impliedVolatility) == 0 &&
               Float.compare(that.strikePrice, strikePrice) == 0 &&
               Float.compare(that.underlyingPrice, underlyingPrice) == 0 &&
               Float.compare(that.yearsToMaturity, yearsToMaturity) == 0 &&
               Double.compare(that.riskFreeRate, riskFreeRate) == 0 &&
               putCall == that.putCall;
    }

    @Override
    public int hashCode() {
        return Objects.hash(putCall, impliedVolatility, strikePrice, underlyingPrice, yearsToMaturity, riskFreeRate);
    }
}
