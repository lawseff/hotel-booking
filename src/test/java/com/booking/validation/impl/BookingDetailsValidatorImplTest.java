package com.booking.validation.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.booking.utils.DateUtils;
import com.booking.validation.api.BookingDetailsValidator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(DataProviderRunner.class)
public class BookingDetailsValidatorImplTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String CURRENT_DATE = "2019-10-15";

    private DateUtils dateUtils;
    private BookingDetailsValidator validator;

    @DataProvider
    public static Object[][] validDataProvider() {
        return new Object[][] {
                { "2019-10-15", "2019-10-16" },
                { "2019-11-25", "2019-12-01" },
                { "2019-12-01", "2019-12-31" },
                { "2020-12-31", "2021-01-07" }
        };
    }

    @DataProvider
    public static Object[][] invalidDataProvider() {
        return new Object[][] {
                { "2019-10-16", "2019-10-16" }, // arrival date is the same as departure date
                { "2019-11-01", "2019-10-31" }, // departure date is before arrival date
                { "2019-09-31", "2019-10-15" }, // arrival date is in the past
                { "2018-01-01", "2018-02-02"} // arrival date and departure date are in the past
        };
    }

    @Before
    public void setUp() throws ParseException {
        dateUtils = mock(DateUtils.class);

        when(dateUtils.getCurrentDateWithoutTime())
                .thenReturn(DATE_FORMAT.parse(CURRENT_DATE));

        validator = new BookingDetailsValidatorImpl(dateUtils);
    }

    @Test
    @UseDataProvider("validDataProvider")
    public void isPeriodOfStayValid_ValidPeriod_True(
            String arrivalDateParameter, String departureDateParameter) throws ParseException {
        // given
        Date arrivalDate = DATE_FORMAT.parse(arrivalDateParameter);
        Date departureDate = DATE_FORMAT.parse(departureDateParameter);

        // when
        boolean isValid = validator.isPeriodOfStayValid(arrivalDate, departureDate);

        // then
        assertTrue(isValid);
        verify(dateUtils, times(1)).getCurrentDateWithoutTime();
        verifyNoMoreInteractions(dateUtils);
    }

    @Test
    @UseDataProvider("invalidDataProvider")
    public void isPeriodOfStayValid_ValidPeriod_False(
            String arrivalDateParameter, String departureDateParameter
    ) throws ParseException {
        // given
        Date arrivalDate = DATE_FORMAT.parse(arrivalDateParameter);
        Date departureDate = DATE_FORMAT.parse(departureDateParameter);

        // when
        boolean isValid = validator.isPeriodOfStayValid(arrivalDate, departureDate);

        // then
        Assert.assertFalse(isValid);
        verify(dateUtils, times(1)).getCurrentDateWithoutTime();
        verifyNoMoreInteractions(dateUtils);
    }

    @Test
    public void isPersonsAmountValid_Valid_True() {
        assertTrue(validator.isPersonsAmountValid(5));
    }

    @DataProvider
    public static Object[][] isPersonsAmountInvalidDataProvider() {
        return new Object[][] {
            {-1}, {0}, {7}
        };
    }
    @Test
    @UseDataProvider("isPersonsAmountInvalidDataProvider")
    public void isPersonsAmountValid_Parameterized_False(int amount) {
        assertFalse(validator.isPersonsAmountValid(amount));
    }

}
