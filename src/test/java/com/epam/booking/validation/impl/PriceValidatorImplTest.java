package com.epam.booking.validation.impl;

import com.epam.booking.validation.api.PriceValidator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.math.BigDecimal;

@RunWith(DataProviderRunner.class)
public class PriceValidatorImplTest {

    private static final BigDecimal VALID_PRICE = new BigDecimal("105.30");

    @DataProvider
    public static Object[][] invalidDataProviderIsPriceValid() {
        return new Object[][] {
                { new BigDecimal("0.00") }, // zero price
                { new BigDecimal("-135.75") } // negative price
        };
    }

    private PriceValidator validator = new PriceValidatorImpl();

    @Test
    public void testIsPriceValidShouldReturnTrueWhenValidPriceSupplied() {
        // given

        // when
        boolean isValid = validator.isPriceValid(VALID_PRICE);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    @UseDataProvider("invalidDataProviderIsPriceValid")
    public void testIsPriceValidShouldReturnFalseWhenInvalidPriceSupplied(BigDecimal price) {
        // given

        // when
        boolean isValid = validator.isPriceValid(price);

        // then
        Assert.assertFalse(isValid);
    }

}
