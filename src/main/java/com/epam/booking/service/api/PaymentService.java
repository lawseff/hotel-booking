package com.epam.booking.service.api;

public interface PaymentService {

    // TODO validation???
    boolean tryToPay(int reservationId, String cardNumber, String validThru, String cvvNumber);

}
