package com.booking.validation.impl;

import org.springframework.stereotype.Component;
import com.booking.validation.api.PriceValidator;
import java.math.BigDecimal;

@Component
public class PriceValidatorImpl implements PriceValidator {

    private static final BigDecimal MAX_PRICE = new BigDecimal("1000.00");

    @Override
    public boolean isPriceValid(BigDecimal price) {
        return getNumberOfDecimalPlaces(price) <= 2
                && price.compareTo(BigDecimal.ZERO) > 0
                && price.compareTo(MAX_PRICE) <= 0;
    }

    private int getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
        String string = bigDecimal.stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

}
