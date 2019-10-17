package com.epam.booking.service.impl;

import com.epam.booking.service.api.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean tryToPay(int reservationId, String cardNumber, String validThru, String cvvNumber) {
        return true;
    }

}
