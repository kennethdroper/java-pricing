package kdr.model;

import kdr.pricing.Option;

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

    public PricingParams(Option.PutCall putCall, double impliedVolatility, float strikePrice, float underlyingPrice, float yearsToMaturity, double riskFreeRate) {
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
}
