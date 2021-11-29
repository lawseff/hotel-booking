package com.booking.validation.impl;

import com.booking.validation.api.PriceValidator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.math.BigDecimal;

@RunWith(DataProviderRunner.class)
public class PriceValidatorImplTest {

    @DataProvider
    public static Object[][] validDataProvider() {
        return new Object[][] {
                { new BigDecimal("10") },
                { new BigDecimal("20.5") },
                { new BigDecimal("50.75") },
                { new BigDecimal("1000.00") }, // max valid price
        };
    }

    @DataProvider
    public static Object[][] invalidDataProvider() {
        return new Object[][] {
                { new BigDecimal("0.00") }, // zero price
                { new BigDecimal("-135.75") }, // negative price
                { new BigDecimal("1000.01") }, // more than 1000.00
                { new BigDecimal("10.123") } // invalid number of digits after the point
        };
    }

    private PriceValidator validator = new PriceValidatorImpl();

    @Test
    @UseDataProvider("validDataProvider")
    public void isPriceValid_ValidPrice_True(BigDecimal price) {
        // given

        // when
        boolean isValid = validator.isPriceValid(price);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    @UseDataProvider("invalidDataProvider")
    public void isPriceValid_InvalidPrice_False(BigDecimal price) {
        // given

        // when
        boolean isValid = validator.isPriceValid(price);

        // then
        Assert.assertFalse(isValid);
    }

}
