package com.epam.booking.validation.impl;

import com.epam.booking.validation.api.PriceValidator;
import java.math.BigDecimal;

public class PriceValidatorImpl implements PriceValidator {

    @Override
    public boolean isPriceValid(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

}
