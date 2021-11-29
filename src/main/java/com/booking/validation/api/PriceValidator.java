package com.booking.validation.api;

import java.math.BigDecimal;

public interface PriceValidator {

    boolean isPriceValid(BigDecimal price);

}
