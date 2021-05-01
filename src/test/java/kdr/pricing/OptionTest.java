package kdr.pricing;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static kdr.util.DateUtility.YEAR_FRACTION_FOR_1_DAY;
import static org.testng.Assert.*;

/**
 * Unit tests for Option class.
 *
 * Year fraction tests are correct if they are less than 1 days tolerance
 * to simplify testing for values which would have many decimal points.
 *
 * Created by kenneth on 23/01/2017.
 */
public class OptionTest {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetTimeToExpirySameTime() throws Exception {
        Calendar now= Calendar.getInstance();
        now.setTime(new Date());

        Date cobDate = now.getTime();
        Date expiryDate = now.getTime();


        Option o = new Option("TestId", "TestCpy", Option.PutCall.CALL, expiryDate, 0.2,
                100.0, 98.0, cobDate);

        assertEquals(o.getTimeToExpiry(), 0.0d);
    }

    @Test
    public void testGetTimeToExpiryNeg() throws Exception {
        Calendar now= Calendar.getInstance();
        now.set(2017, 0, 1);
        Date cobDate = now.getTime();

        Calendar sixMonthsEarlier= Calendar.getInstance();
        sixMonthsEarlier.set(2016, 6, 2);
        Date expiryDate = sixMonthsEarlier.getTime();

        Option o = new Option("TestId", "TestCpy", Option.PutCall.CALL, expiryDate, 0.2,
                100.0, 98.0, cobDate);

        double expected= -0.5d;
        double expiry = o.getTimeToExpiry();
        assertTrue((Math.abs(expiry - expected) < YEAR_FRACTION_FOR_1_DAY),
                "expiry: " + expiry + " must be lesss than expected: " + expected + " less " + YEAR_FRACTION_FOR_1_DAY
        );    }

    @Test
    public void testGetTimeToExpiryHalfYear() throws Exception {
        Calendar now= Calendar.getInstance();
        now.set(2017, 0, 1, 0, 0, 0);
        Date cobDate = now.getTime();
        System.out.printf("cobDate: %s", cobDate);

        // First 6 months of the year is not actually half way on a non-leap year, needs to be +1 day
        Calendar sixMonthsLater= Calendar.getInstance();
        sixMonthsLater.set(2017, 6, 2, 0, 0, 0);
        Date expiryDate = sixMonthsLater.getTime();
        System.out.printf("expiryDate: %s", expiryDate);

        Option o = new Option("TestId", "TestCpy", Option.PutCall.CALL, expiryDate, 0.2,
                100.0, 98.0, cobDate);

        double expected= 0.5d;
        double expiry = o.getTimeToExpiry();
        assertTrue((Math.abs(expiry - expected) < YEAR_FRACTION_FOR_1_DAY),
                "expiry: " + expiry + " must be lesss than expected: " + expected + " less " + YEAR_FRACTION_FOR_1_DAY
                );
    }

    @Test
    public void testGetTimeToExpiryFullYear() throws Exception {
        Calendar now = Calendar.getInstance();
        now.set(2017, 0, 1, 0, 0, 0);
        Date cobDate = now.getTime();
        System.out.printf("cobDate: %s", cobDate);

        Calendar oneYearLater = Calendar.getInstance();
        oneYearLater.set(2018, 0, 1, 0, 0, 0);
        Date expiryDate = oneYearLater.getTime();
        System.out.printf("expiryDate: %s", expiryDate);

        Option o = new Option("TestId", "TestCpy", Option.PutCall.CALL, expiryDate, 0.2,
                100.0, 98.0, cobDate);

        double expected = 1.0d;
        double expiry = o.getTimeToExpiry();
        assertTrue((Math.abs(expiry - expected) < YEAR_FRACTION_FOR_1_DAY),
                "expiry: " + expiry + " must be lesss than expected: " + expected + " less " + YEAR_FRACTION_FOR_1_DAY
        );
    }


}