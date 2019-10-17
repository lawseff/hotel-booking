package com.epam.booking.validation.impl;

import com.epam.booking.utils.DateUtils;
import com.epam.booking.validation.api.BookingDetailsValidator;
import java.util.Date;

public class BookingDetailsValidatorImpl implements BookingDetailsValidator {

    private static final int MAXIMUM_ROOM_CAPACITY = 6;

    private DateUtils dateUtils;

    public BookingDetailsValidatorImpl(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    @Override
    public boolean isPeriodOfStayValid(Date arrivalDate, Date departureDate) {
        Date now = dateUtils.getCurrentDateWithoutTime();
        return !arrivalDate.before(now) && departureDate.after(arrivalDate);
    }

    @Override
    public boolean isPersonsAmountValid(int personsAmount) {
        return personsAmount > 0 && personsAmount <= MAXIMUM_ROOM_CAPACITY;
    }

}
