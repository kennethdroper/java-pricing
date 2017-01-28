package kdr.pricing;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.*;

/**
 * Created by kenneth on 23/01/2017.
 */
public class BlackScholesPricerTest {

    @BeforeMethod
    public void setUp() {

    }

    @Test
    public void testITMCallsOnExpiryDate() throws Exception {

        testPriceCallOnExpiryDate(100.0, 150.0);
        testPriceCallOnExpiryDate(50.0, 225.0);
        testPriceCallOnExpiryDate(1.0, 1.0);
        testPriceCallOnExpiryDate(200.0, 250.0);
        testPriceCallOnExpiryDate(50.0, 200.0);

    }


    /** Utility method to flex the strike and underlying prices when the
     * expiry date is today.
     * @param strike
     * @param underlyingPrice
     * @throws Exception
     */
    private void testPriceCallOnExpiryDate(double strike, double underlyingPrice) throws Exception {
        // use a Calendar to get exactly the same millis in the date
        Calendar now= Calendar.getInstance();
        now.setTime(new Date());

        Date cobDate = now.getTime();
        Date expiryDate = now.getTime();


        Option o = new Option("TestId", "TestCpy", Option.PutCall.CALL, expiryDate, 0.2,
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
        assertEquals(r.callMTM, (underlyingPrice - strike) / 2);
    }

}