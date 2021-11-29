package web.utils;

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
public class DateUtilsTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private DateUtils dateUtils = new DateUtils();

    @DataProvider
    public static Object[][] dataProviderCalculateDaysBetweenDates() {
        return new Object[][] {
                { "2019-11-23", "2019-11-27", 4 },
                { "2019-12-31", "2020-01-01", 1 },
                { "2019-10-01", "2019-10-16", 15 }
        };
    }

    @DataProvider
    public static Object[][] trueDataProviderIsBetweenDates() {
        return new Object[][] {
                { "2019-10-15", "2019-10-14", "2019-10-16" },
                { "2020-03-13", "2020-03-13", "2020-03-14" },
                { "2019-11-05", "2019-11-01", "2019-11-05" }
        };
    }

    @DataProvider
    public static Object[][] falseDataProviderIsBetweenDates() {
        return new Object[][] {
                { "2018-01-01", "2019-05-07", "2019-06-15" },
                { "2020-01-01", "2019-05-07", "2019-06-15" }
        };
    }

    @Test
    @UseDataProvider("dataProviderCalculateDaysBetweenDates")
    public void calculateDaysBetweenDates_Parameterized_DaysReturned(
            String firstDateParameter, String secondDateParameter, int expectedDays) throws ParseException {
        // given
        Date firstDate = DATE_FORMAT.parse(firstDateParameter);
        Date secondDate = DATE_FORMAT.parse(secondDateParameter);

        // when
        int days = dateUtils.calculateDaysBetweenDates(firstDate, secondDate);

        // then
        Assert.assertEquals(expectedDays, days);
    }

    @Test
    @UseDataProvider("trueDataProviderIsBetweenDates")
    public void issBetweenDates_BetweenDates_True(
            String dateParameter, String startParameter, String endParameter) throws ParseException {
        // given
        Date date = DATE_FORMAT.parse(dateParameter);
        Date start = DATE_FORMAT.parse(startParameter);
        Date end = DATE_FORMAT.parse(endParameter);

        // when
        boolean betweenDates = dateUtils.isBetweenDates(date, start, end);

        // then
        Assert.assertTrue(betweenDates);
    }

    @Test
    @UseDataProvider("falseDataProviderIsBetweenDates")
    public void isBetweenDates_NotInBetween_False(
            String dateParameter, String startParameter, String endParameter) throws ParseException {
        // given
        Date date = DATE_FORMAT.parse(dateParameter);
        Date start = DATE_FORMAT.parse(startParameter);
        Date end = DATE_FORMAT.parse(endParameter);

        // when
        boolean betweenDates = dateUtils.isBetweenDates(date, start, end);

        // then
        Assert.assertFalse(betweenDates);
    }

}
