package com.booking.service;

import com.booking.exception.ServiceException;
import java.util.Date;

public interface BookService {

    void book(Date arrivalDate, Date departureDate, Integer personsAmount, String roomClass) throws ServiceException;

}
