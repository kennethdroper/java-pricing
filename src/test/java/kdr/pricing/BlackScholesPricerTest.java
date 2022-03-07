package kdr.pricing;

import kdr.model.PricingParams;
import kdr.model.PricingResult;
import kdr.util.DateUtility;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * Unit tests for BlackScholesPricer.
 *
 * Comparisons drawn up with calculators here:
 *
 * http://www.investopedia.com/university/options-pricing/black-scholes-model.asp
 * http://www.fintools.com/resources/online-calculators/options-calcs/options-calculator/
 *
 * Created by kenneth on 23/01/2017.
 */
public class BlackScholesPricerTest {

    private double riskFreeRateOn20170207 = 0.026;

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void testCallsOnExpiryDate() throws Exception {
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170207";
        double riskFreeRate = 0.2;

        testOptionPrice(Option.PutCall.CALL, 100.0f, 150.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);

        testOptionPrice(Option.PutCall.CALL, 500.0f, 10.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);
    }

    @Test
    public void testPutsOnExpiryDate() throws Exception {
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170207";
        double riskFreeRate = 0.2;

        testOptionPrice(Option.PutCall.PUT, 100.0f, 150.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);

        testOptionPrice(Option.PutCall.PUT, 500.0f, 10.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);
    }


    @Test
    public void testPutsATMOnExpiry() throws Exception {

        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170207";
        double riskFreeRate = 0.2;

        testOptionPrice(Option.PutCall.PUT, 1.0f, 1.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);

        testOptionPrice(Option.PutCall.PUT, 200.0f, 200.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);


        testOptionPrice(Option.PutCall.PUT, 52.1f, 52.1f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);
    }


    @Test
    public void testCallsATMOnExpiry() throws Exception {

        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170207";
        double riskFreeRate = 0.2;

        testOptionPrice(Option.PutCall.CALL, 1.0f, 1.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);

        testOptionPrice(Option.PutCall.CALL, 200.0f, 200.0f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);


        testOptionPrice(Option.PutCall.CALL, 52.1f, 52.1f,
                0.10f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRate,
                0.0);
    }

    /**
     * http://finance.yahoo.com/quote/GOOG/options?p=GOOG&date=1490313600
     */
    @Test
    public void testGOOGDataStrike8000() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 800f, underlyingPrice,
                0.1641f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                25.4895);

        testOptionPrice(Option.PutCall.PUT, 800f, underlyingPrice,
                0.1641f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                12.8292);
    }

    @Test
    public void testGOOGDataStrike8025() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 802.5f, underlyingPrice,
                0.1376f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                21.1262);

        testOptionPrice(Option.PutCall.PUT, 802.5f, underlyingPrice,
                0.1376f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                10.9579);
    }

    @Test
    public void testGOOGDataStrike8050() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 805f, underlyingPrice,
                0.1652f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                22.74377);

    }

    @Test
    public void testGOOGDataStrike8010() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 810f, underlyingPrice,
                0.1371f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                16.9136);

        testOptionPrice(Option.PutCall.PUT, 810f, underlyingPrice,
                0.1371f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                14.2213);

    }

    @Test
    public void testGOOGDataStrikeOTM() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 820f, underlyingPrice,
                0.1371f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                12.2569);

        testOptionPrice(Option.PutCall.PUT, 820f, underlyingPrice,
                0.1371f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                19.5326);

    }


    private void testOptionPrice(Option.PutCall putCall, float strike, float underlyingPrice,
                                 float impliedVol,
                                 String cobYYYYMMDD, String expiryYYYYMMDD, double riskFreeRate,
                                 double expectedMTM) {

        // Test constant within which doubles are considered equal
        final double EPSILON = 0.0001d;

        Date cobDate= DateUtility.parseYYYYMMDD(cobYYYYMMDD);
        Date expiryDate = DateUtility.parseYYYYMMDD(expiryYYYYMMDD);

        Option o = new Option("TestId", "TestCpy", putCall, expiryDate, impliedVol,
                strike, underlyingPrice, cobDate);

        BlackScholesPricer.BlackScholesAnalytics r= BlackScholesPricer.getAnalytics(
                o.getPutCall(),
                o.getImpliedVol(),
                (float) o.getStrikePrice(),
                (float) o.getUnderlyingPrice(),
                (float) o.getTimeToExpiry(),
                riskFreeRate);

        assert(r != null);

        // The price of an option on expiry is half the difference between the price of the underlying and the strike
        // This gives negative prices, which obviously would never be observable.
        if (putCall == Option.PutCall.CALL) {
            assertEquals(r.callMTM, expectedMTM, EPSILON);
        }
        else
        {
            assertEquals(r.putMTM, expectedMTM, EPSILON);
        }

    }


    @Test
    public void testAlignmentOfOverriddenPricingMethods() throws Exception {

        float yearsToMaturity=2;
        double riskFreeRate = 0.2;

        // Test ATM
        testPrimitiveVsObjectTypes(Option.PutCall.PUT, 1.0f, 1.0f,
                0.10f, yearsToMaturity, riskFreeRate);

        // Test Call
        testPrimitiveVsObjectTypes(Option.PutCall.CALL, 1.0f, 66.0f,
                0.10f, 2.5f, riskFreeRate);

        // Test Put
        testPrimitiveVsObjectTypes(Option.PutCall.PUT, 1.0f, 100.0f,
                0.10f, 0.6f, riskFreeRate);
    }

    private void testPrimitiveVsObjectTypes(Option.PutCall putCall, float strike, float underlyingPrice,
                                 float impliedVol,
                                 float yearsToMaturity,
                                 double riskFreeRate) {

        // Test constant within which doubles are considered equal
        final double EPSILON = 0.0001d;

        // ParameterisedMethod
        PricingParams params= new PricingParams(putCall, strike, underlyingPrice, impliedVol, yearsToMaturity, riskFreeRate);
        PricingResult pricingResult= BlackScholesPricer.priceOption(params);
        assert(pricingResult != null);

        // Primitive parameterised method
        BlackScholesPricer.BlackScholesAnalytics r= BlackScholesPricer.getAnalytics(
                putCall, strike, underlyingPrice, impliedVol, yearsToMaturity, riskFreeRate);

        // The price of an option on expiry is half the difference between the price of the underlying and the strike
        // This gives negative prices, which obviously would never be observable.
        if (putCall == Option.PutCall.CALL) {
            assertEquals(r.callMTM, pricingResult.getMtm(), EPSILON);
        }
        else
        {
            assertEquals(r.putMTM, pricingResult.getMtm(), EPSILON);
        }

    }
    
}