package com.epam.booking.validation.impl;

import com.epam.booking.validation.api.PaymentValidator;

public class PaymentValidatorImpl implements PaymentValidator {

    private static final String CARD_NUMBER_PATTERN = "\\d{16}";

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return false;
    }

    @Override
    public boolean isExpirationDateValid(String expirationDate) {
        return false;
    }

    @Override
    public boolean isCvvNumberValid(String cvvNumber) {
        return false;
    }

}
