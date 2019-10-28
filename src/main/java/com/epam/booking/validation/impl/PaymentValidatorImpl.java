package com.epam.booking.validation.impl;

import com.epam.booking.utils.DateUtils;
import com.epam.booking.validation.api.PaymentValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentValidatorImpl implements PaymentValidator {

    // card number
    private static final String CARD_NUMBER_REGEX = "\\d{16}";
    // expiration date
    private static final String EXPIRATION_DATE_REGEX = "(0[1-9]|1[0-2])/\\d{2}";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/yy");
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

        Date currentDate = dateUtils.getCurrentMonthAndYear();
        try {
            Date cardExpirationDate = DATE_FORMAT.parse(expirationDate);
            return !cardExpirationDate.before(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean isCvvNumberValid(String cvvNumber) {
        return cvvNumber.matches(CVV_NUMBER_REGEX);
    }

}
