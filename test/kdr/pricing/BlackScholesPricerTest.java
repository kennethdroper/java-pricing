package kdr.pricing;

import kdr.util.DateUtility;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * Unit tests for BlackScholesPricer.
 *
 * Created by kenneth on 23/01/2017.
 */
public class BlackScholesPricerTest {

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void testCallsOnExpiryDate() throws Exception {

        // In the money options
        testPriceCallOnExpiryDate(Option.PutCall.CALL, 100.0, 150.0);
        testPriceCallOnExpiryDate(Option.PutCall.CALL,50.0, 225.0);
        testPriceCallOnExpiryDate(Option.PutCall.CALL,1.0, 1.0);
        testPriceCallOnExpiryDate(Option.PutCall.CALL,200.0, 250.0);
        testPriceCallOnExpiryDate(Option.PutCall.CALL,50.0, 200.0);

        // Out The Money options
        testPriceCallOnExpiryDate(Option.PutCall.CALL,250.0, 200.0);
        testPriceCallOnExpiryDate(Option.PutCall.CALL,500.0, 200.0);

    }

    @Test
    public void testPutsOnExpiryDate() throws Exception {

        // ITM options
        testPriceCallOnExpiryDate(Option.PutCall.PUT, 100.0, 150.0);
        testPriceCallOnExpiryDate(Option.PutCall.PUT,50.0, 225.0);
        testPriceCallOnExpiryDate(Option.PutCall.PUT,1.0, 1.0);
        testPriceCallOnExpiryDate(Option.PutCall.PUT,200.0, 250.0);
        testPriceCallOnExpiryDate(Option.PutCall.PUT,50.0, 200.0);

        // OTM options
        testPriceCallOnExpiryDate(Option.PutCall.PUT,250.0, 200.0);
        testPriceCallOnExpiryDate(Option.PutCall.PUT,500.0, 200.0);

    }

    /** Utility method to flex the strike and underlying prices when the
     * expiry date is today.  Vol is held constant.
     * @param strike strike price
     * @param underlyingPrice underlying prices.
     */
    private void testPriceCallOnExpiryDate(Option.PutCall putCall, double strike, double underlyingPrice) throws Exception {
        // use a Calendar to get exactly the same millis in the date
        Calendar now= Calendar.getInstance();
        now.setTime(new Date());

        Date cobDate = now.getTime();
        Date expiryDate = now.getTime();


        Option o = new Option("TestId", "TestCpy", putCall, expiryDate, 0.2,
                strike, underlyingPrice, cobDate);

        double riskFreeRate = 0.5;

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
        assertEquals(r.callMTM, (underlyingPrice - strike) / 2);
    }

    private double riskFreeRateOn20170207 = 0.26;

    /**
     * http://finance.yahoo.com/quote/GOOG/options?p=GOOG&date=1490313600
     */
    @Test
    public void testGOOGDataCall8000() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 800f, underlyingPrice,
                0.1641f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (23.3 + 22.1) / 2f);

    }

    @Test
    public void testGOOGDataCall8025() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 802.5f, underlyingPrice,
                0.1376f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (18.8 + 17.6) / 2f);

    }

    @Test
    public void testGOOGDataCall8050() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 805f, underlyingPrice,
                0.1652f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (20.6 + 19.4) / 2f);

    }

    @Test
    public void testGOOGDataCall8010() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.CALL, 810f, underlyingPrice,
                0.1371f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (14.9 + 13.9) / 2f);

    }

    @Test
    public void testGOOGDataPut8000() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.PUT, 800f, underlyingPrice,
                0.1560f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (12.9 + 13.9) / 2f);

    }

    @Test
    public void testGOOGDataPut785() {
        float underlyingPrice = 810.10f;
        String cobYYYYMMDD = "20170207";
        String expiryYYYYMMDD = "20170324";

        testOptionPrice(Option.PutCall.PUT, 785f, underlyingPrice,
                0.1934f, cobYYYYMMDD, expiryYYYYMMDD, riskFreeRateOn20170207,
                (12.1 + 11.0) / 2f);

    }

    /**
     * Sample data from https://invento.quora.com/Advanced-Black-Scholes-calculation-with-a-real-example
     */
    @Test
    public void testGOOGDataCallApple() {
        float underlyingPrice = 460f;
        String cobYYYYMMDD = "20170101";
        String expiryYYYYMMDD = "20170304";

        testOptionPrice(Option.PutCall.CALL, 470f, 460f,
                0.58f, cobYYYYMMDD, expiryYYYYMMDD, 0.02,
                37.0);

    }

    private void testOptionPrice(Option.PutCall putCall, float strike, float underlyingPrice,
                                 float impliedVol,
                                 String cobYYYYMMDD, String expiryYYYYMMDD, double riskFreeRate,
                                 double expectedMTM) {
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
            assertEquals(r.callMTM, expectedMTM);
        }
        else
        {
            assertEquals(r.putMTM, expectedMTM);
        }

    }


}