package com.epam.booking.validation.impl;

import com.epam.booking.utils.DateUtils;
import com.epam.booking.validation.api.PaymentValidator;

public class PaymentValidatorImpl implements PaymentValidator {

    // card number
    private static final String CARD_NUMBER_REGEX = "\\d{16}";
    // expiration date
    private static final String EXPIRATION_DATE_REGEX = "(0[1-9]|1[0-2])/\\d{2}";
    private static final int EXPIRATION_DATE_DELIMITER_INDEX = 2;
    private static final int BEGIN_YEAR = 2000;
    // cvv number
    private static final String CVV_NUMBER_REGEX = "\\d{3}";

    private DateUtils dateUtils;

    public PaymentValidatorImpl(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber.matches(CARD_NUMBER_REGEX);
        // TODO Luhn algorithm
    }

    @Override
    public boolean isExpirationDateValid(String expirationDate) {
        if (!expirationDate.matches(EXPIRATION_DATE_REGEX)) {
            return false;
        }
        int month = parseMonth(expirationDate);
        int year = parseYear(expirationDate);
        int currentMonth = dateUtils.getCurrentMonth();
        int currentYear = dateUtils.getCurrentYear();
        return year > currentYear || year == currentYear && month >= currentMonth;
    }

    @Override
    public boolean isCvvNumberValid(String cvvNumber) {
        return cvvNumber.matches(CVV_NUMBER_REGEX);
    }

    private int parseMonth(String expirationDate) {
        String month = expirationDate.substring(0, EXPIRATION_DATE_DELIMITER_INDEX);
        return Integer.parseInt(month);
    }

    private int parseYear(String expirationDate) {
        int beginIndex = EXPIRATION_DATE_DELIMITER_INDEX + 1;
        String year = expirationDate.substring(beginIndex);
        return BEGIN_YEAR + Integer.parseInt(year);
    }

}
