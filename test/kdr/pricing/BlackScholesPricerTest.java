package kdr.pricing;

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

}