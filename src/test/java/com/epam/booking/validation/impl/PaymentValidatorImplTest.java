package com.epam.booking.validation.impl;

import static org.mockito.Mockito.*;
import com.epam.booking.utils.DateUtils;
import com.epam.booking.validation.api.PaymentValidator;
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
public class PaymentValidatorImplTest {

    // card number
    private static final String VALID_CARD_NUMBER = "4024007122491607";
    // expiration date
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/yy");
    private static final String CURRENT_DATE = "10/19";
    private static final String VALID_EXPIRATION_DATE ="08/22";
    // cvv number
    private static final String VALID_CVV_NUMBER = "123";

    private PaymentValidator validator;

    @DataProvider
    public static Object[][] invalidDataProviderIsCardNumberValid() {
        return new Object[][] {
                { "" }, // empty string
                { "123456789012345" }, // 15 digits
                { "12345678901234567" }, // 17 digits
                { "1234_5678901234x" } // invalid characters
        };
    }

    @DataProvider
    public static Object[][] invalidDataProviderIsExpirationDateValid() {
        return new Object[][] {
                { "" }, // empty string
                { "19/25" }, // invalid month
                { "07_21" } // invalid character
        };
    }

    @DataProvider
    public static Object[][] invalidDataProviderIsCvvNumberValid() {
        return new Object[][] {
                { "" }, // empty string
                { "1234" }, // invalid length
                { "1_3" } // invalid character
        };
    }

    @Before
    public void createMock() throws ParseException {
        DateUtils dateUtils = mock(DateUtils.class);
        Date date = DATE_FORMAT.parse(CURRENT_DATE);
        when(dateUtils.getCurrentMonthAndYear())
                .thenReturn(date);
        validator = new PaymentValidatorImpl(dateUtils);
    }

    @Test
    public void testIsCardNumberValidShouldReturnTrueWhenValidCardNumberSupplied() {
        // given

        // when
        boolean isValid = validator.isCardNumberValid(VALID_CARD_NUMBER);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    @UseDataProvider("invalidDataProviderIsCardNumberValid")
    public void testIsCardNumberValidShouldReturnFalseWhenInvalidCardNumberSupplied(String cardNumber) {
        // given

        // when
        boolean isValid = validator.isCardNumberValid(cardNumber);

        // then
        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsExpirationDateValidShouldReturnTrueWhenValidExpirationDateSupplied() {
        // given

        // when
        boolean isValid = validator.isExpirationDateValid(VALID_EXPIRATION_DATE);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsExpirationDateValidShouldReturnTrueWhenCurrentDateSupplied() {
        // given

        // when
        boolean isValid = validator.isExpirationDateValid(CURRENT_DATE);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    @UseDataProvider("invalidDataProviderIsExpirationDateValid")
    public void testIsExpirationDateValidShouldReturnFalseWhenInvalidExpirationDateSupplied(String expirationDate) {
        // given

        // when
        boolean isValid = validator.isExpirationDateValid(expirationDate);

        // then
        Assert.assertFalse(isValid);
    }

    @Test
    public void testIsCvvNumberValidShouldReturnTrueWhenValidCvvNumberSupplied() {
        // given

        // when
        boolean isValid = validator.isCvvNumberValid(VALID_CVV_NUMBER);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    @UseDataProvider("invalidDataProviderIsCvvNumberValid")
    public void testIsCvvNumberValidShouldReturnTrueWhenValidCvvNumberSupplied(String cvvNumber) {
        // given

        // when
        boolean isValid = validator.isExpirationDateValid(cvvNumber);

        // then
        Assert.assertFalse(isValid);
    }

}
