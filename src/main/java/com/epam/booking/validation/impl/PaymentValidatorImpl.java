package com.epam.booking.validation.impl;

import com.epam.booking.validation.api.PaymentValidator;

public class PaymentValidatorImpl implements PaymentValidator {

    private static final String CARD_NUMBER_REGEX = "\\d{16}";
    private static final String EXPIRATION_DATE_REGEX = "(0[1-9]|1[0-2])/\\d{2}";
    private static final String CVV_NUMBER_REGEX = "\\d{3}";

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber.matches(CARD_NUMBER_REGEX);
        // TODO Luhn algorithm
    }

    @Override
    public boolean isExpirationDateValid(String expirationDate) {
        return expirationDate.matches(EXPIRATION_DATE_REGEX);
    }

    @Override
    public boolean isCvvNumberValid(String cvvNumber) {
        return cvvNumber.matches(CVV_NUMBER_REGEX);
    }

}
