package kdr.pricing;

import java.lang.Math;
import org.apache.commons.math3.distribution.NormalDistribution;
import kdr.pricing.Option.PutCall;

/**
 * Implementation of Black Scholes option pricing algorithm.
 *
 * Specs:
 * https://en.wikipedia.org/wiki/Black%E2%80%93Scholes_model#Black.E2.80.93Scholes_formula
 *
 * Created by kennethroper on 14/01/2017.
 */
public class BlackScholesPricer {

    public final static class BlackScholesAnalytics {
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
     * Pricing method for European options
     *
     * @param putOrCall the option type
     * @param v the implied volatility, sigma
     * @param k the strike price
     * @param s the underlying spot stock price
     * @param t the time to maturity of the European option, in years
     * @param rate the risk free rate
     * @return the MTM of the option according to the standard Black Scholes formula
     */
    public static double priceOption(PutCall putOrCall, double v, float k, float s, float t, double rate) {

        long time1 = System.currentTimeMillis();

        BlackScholesAnalytics r = new BlackScholesAnalytics();

        r.d1 =
                1 / v * Math.sqrt(t) * (
                        Math.log(s / k) + (rate + (Math.pow(v, 2) / 2))
                                * t);

        r.d2 =
                r.d1 - v * Math.sqrt(t);

        long time2= System.currentTimeMillis();
        System.out.printf("Calc d1, d2 in %dms\n", time2 - time1);

        // Create new standard normal distribution, mean is 0 and variance is 1
        NormalDistribution nd = new NormalDistribution();

        long time2b= System.currentTimeMillis();
        System.out.printf("Constructed distribution in %dms\n", time2b - time2);

        r.cumulativeDistributionD1= nd.cumulativeProbability(r.d1);
        r.cumulativeDistributionD2= nd.cumulativeProbability(r.d2);
        r.discountFactor = Math.pow(Math.E, (-rate * t));

        r.callMTM= r.cumulativeDistributionD1 * s - r.cumulativeDistributionD2 * k * r.discountFactor;

        long time3= System.currentTimeMillis();

        System.out.printf("Calced MTM of Call in %dms\n", time3 - time2b);

        if (putOrCall == PutCall.CALL) {
            System.out.printf("Call analytics: %s", r.toString());
            return r.callMTM;
        }
        else
        {
            r.negCumulativeDistributionD1= nd.cumulativeProbability(-r.d1);
            r.negCumulativeDistributionD2= nd.cumulativeProbability(-r.d2);
            r.putMTM = r.negCumulativeDistributionD2 * k * r.discountFactor - r.negCumulativeDistributionD1 * s;
            long time4= System.currentTimeMillis();
            System.out.printf("Calced MTM of Put in %d.\n Put analytics: %s", time4 - time3, r.toString());
            return r.putMTM;
        }
    }
}
