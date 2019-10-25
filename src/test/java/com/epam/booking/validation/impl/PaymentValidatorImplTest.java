package com.epam.booking.validation.impl;

import com.epam.booking.validation.api.PaymentValidator;
import org.junit.Assert;
import org.junit.Test;

public class PaymentValidatorImplTest {

    private static final String VALID_CARD_NUMBER = "4024007122491607";

    private PaymentValidator validator = new PaymentValidatorImpl();

    @Test
    public void testIsCardNumberValidShouldReturnTrueWhenValidCardNumberSupplied() {
        // given

        // when
        boolean isValid = validator.isCardNumberValid(VALID_CARD_NUMBER);

        // then
        Assert.assertTrue(isValid);
    }

    @Test
    public void testIsCardNumberValidShouldReturnFalseWhenValidCardNumberSupplied() {
        // given

        // when

        // then
    }

}
