package kdr.pricing;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import kdr.pricing.Option.PutCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static kdr.pricing.Option.PutCall.CALL;

/**
 * Implementation of Black Scholes option pricing algorithm.
 *
 * Specs:
 * https://en.wikipedia.org/wiki/Black%E2%80%93Scholes_model#Black.E2.80.93Scholes_formula
 *
 * Created by kennethroper on 14/01/2017.
 */
public class BlackScholesPricer {

    /**
     * Package level structure to group together the mid-results of the pricer
     */
    final static class BlackScholesAnalytics {
        double d1, d2, discountFactor, cumulativeDistributionD1, cumulativeDistributionD2, callMTM,
                negCumulativeDistributionD1, negCumulativeDistributionD2, putMTM;

        public String toString() {
            return "****** d1: " + d1
                    + "\nd2: " + d2
                    + "\ndiscountFactor: " + discountFactor
                    + "\ncumulativeDistributionD1: " + cumulativeDistributionD1
                    + "\ncumulativeDistributionD2: " + cumulativeDistributionD2
                    + "\ncallMTM: " + callMTM
                    + "\n(negCumulativeDistributionD1): " + negCumulativeDistributionD1
                    + "\n(negCumulativeDistributionD2): " + negCumulativeDistributionD2
                    + "\n(putMTM):" + putMTM;
        }
    }

    /**
     * Package level implementation method for ease of unit testing.
     *
     * @param putOrCall @see #priceOption
     * @param v @see #priceOption
     * @param k @see #priceOption
     * @param s @see #priceOption
     * @param t @see #priceOption
     * @param rate @see #priceOption
     * @throws IllegalArgumentException if the given parameters cannot be priced.
     * @return BlackScholesAnalytics with pricing information
     */
    static BlackScholesAnalytics getAnalytics(PutCall putOrCall, double v, float k, float s, float t, double rate) {
        final Logger logger = LoggerFactory.getLogger(BlackScholesPricer.class);

        List<String> errors= validatePricingParameters(putOrCall, v, k, s, t, rate);


        if (errors != null) throw new IllegalArgumentException(errors.toString());

        logger.info("Pricing  putOrCall:{}, v:{}, k:{}, s:{}, t:{}, rate:{}", putOrCall, v, k, s, t, rate);

        long time1 = System.currentTimeMillis();

        BlackScholesAnalytics r = new BlackScholesAnalytics();
/*
        r.d1 =
                1 / v * Math.sqrt(t) * (
                        Math.log(s / k) +
                                (rate + (Math.pow(v, 2) / 2)) * t);
*/

        double log_sk = Math.log(s / k);
        double pow_v2 = Math.pow(v, 2);
        double d1num = log_sk + (rate + (pow_v2 / 2)) * t;

        double v_times_root_t = v * Math.sqrt(t);

        // Avoid NaN due to divide by zero error.  No time left means no price.
        if (v_times_root_t == 0) return r;

        r.d1 = d1num / v_times_root_t;

        r.d2 = r.d1 - v_times_root_t;

        long time2 = System.currentTimeMillis();
        logger.info("Calc d1, d2 in {}ms", time2 - time1);

        // Create new standard normal distribution, mean is 0 and variance is 1
        NormalDistribution nd = new NormalDistribution();

        long time2b = System.currentTimeMillis();
        logger.info("Constructed distribution in {}ms", time2b - time2);

        r.cumulativeDistributionD1 = nd.cumulativeProbability(r.d1);
        r.cumulativeDistributionD2 = nd.cumulativeProbability(r.d2);
        r.discountFactor = Math.exp(-rate * t);

        r.callMTM = r.cumulativeDistributionD1 * s - r.cumulativeDistributionD2 * k * r.discountFactor;

        long time3 = System.currentTimeMillis();

        logger.info("Calced MTM of Call in {}ms", time3 - time2b);

        if (putOrCall == CALL) {
            logger.debug("Call analytics: {}", r.toString());
        } else {
            r.negCumulativeDistributionD1 = nd.cumulativeProbability(-r.d1);
            r.negCumulativeDistributionD2 = nd.cumulativeProbability(-r.d2);

            // Alternative implementation without reusing call MTM:
            // r.putMTM = r.negCumulativeDistributionD2 * k * r.discountFactor - r.negCumulativeDistributionD1 * s;

            r.putMTM = k * r.discountFactor - s + r.callMTM;
            long time4 = System.currentTimeMillis();
            logger.info("Calced MTM of Put in {}.\nPut analytics: {}", time4 - time3, r.toString());
        }

        return r;
    }

    /**
     * Pricing method for European options
     *
     * @param putOrCall the option type
     * @param v         the implied volatility, sigma
     * @param k         the strike price
     * @param s         the underlying spot stock price
     * @param t         the time to maturity of the European option, in years
     * @param rate      the risk free rate
     * @return the MTM of the option according to the standard Black Scholes formula
     */
    public static double priceOption(PutCall putOrCall, double v, float k, float s, float t, double rate)
    {

        BlackScholesAnalytics r = BlackScholesPricer.getAnalytics(putOrCall, v, k, s, t, rate);

        return putOrCall == CALL ? r.callMTM : r.putMTM;

    }


    static double calcDelta(PutCall putCall, double d1) {
        throw new UnsupportedOperationException("Delta calculation is not yet implemented.");
    }

    /**
     * Validate the pre-conditions of the pricer.
     *
     * Package-level for unit testing.
     *
     * @param putOrCall @see #priceOption
     * @param v @see #priceOption
     * @param k @see #priceOption
     * @param s @see #priceOption
     * @param t @see #priceOption
     * @param rate @see #priceOption
     * @throws IllegalArgumentException if the parameters provided cannot be priced.
     * @return null if the parameters provided are valid, else a List of error messages
     */
    static List<String> validatePricingParameters(PutCall putOrCall, double v, float k, float s, float t, double rate) {
        List<String> messages= new ArrayList<String>(5);

        if(putOrCall == null) messages.add("putOrCall cannot be null");
        if (v < 0.0 || Double.isNaN(v)) messages.add("Volatility cannot be negative.  v = " + v);
        if (k < 0.0 || Float.isNaN(k)) messages.add("Strike price cannot be negative.  k = " + k);
        if (s < 0.0 || Float.isNaN(s)) messages.add("Underlying's price cannot be negative.  s = " + s);
        if (t < 0.0 || Float.isNaN(t)) messages.add("Time to maturity cannot be negative or NaN.  t = " + t);

        // Negative rates are not deemed invalid
        if (Double.isNaN(rate)) messages.add("Rate cannot be  NaN.  rate = " + rate);
        return (messages.isEmpty() ? null : messages);
    }
}
