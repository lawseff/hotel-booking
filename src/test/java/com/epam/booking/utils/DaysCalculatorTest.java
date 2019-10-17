package com.epam.booking.utils;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(DataProviderRunner.class)
public class DaysCalculatorTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private DaysCalculator calculator = new DaysCalculator();

    @DataProvider
    public static Object[][] dataProviderCalculateDaysBetweenDates() {
        return new Object[][] {
                { "2019-11-23", "2019-11-27", 4 },
                { "2019-12-31", "2020-01-01", 1 },
                { "2019-10-01", "2019-10-16", 15 }
        };
    }

    @Test
    @UseDataProvider("dataProviderCalculateDaysBetweenDates")
    public void testCalculateDaysBetweenDatesShouldReturnDaysWhenDatesSupplied(
            String firstDateParameter, String secondDateParameter, int expectedDays) throws ParseException {
        // given
        Date firstDate = DATE_FORMAT.parse(firstDateParameter);
        Date secondDate = DATE_FORMAT.parse(secondDateParameter);

        // when
        int days = calculator.calculateDaysBetweenDates(firstDate, secondDate);

        // then
        Assert.assertEquals(expectedDays, days);
    }

}
