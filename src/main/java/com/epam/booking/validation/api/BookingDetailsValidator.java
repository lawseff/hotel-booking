package com.epam.booking.validation.api;

import java.util.Date;

public interface BookingDetailsValidator {

    boolean isPeriodOfStayValid(Date arrivalDate, Date departureDate);
    boolean isPersonsAmountValid(int personsAmount);

}
