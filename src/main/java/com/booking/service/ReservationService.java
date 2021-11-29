package com.booking.service;

import com.booking.exception.ServiceException;
import org.springframework.ui.Model;

public interface ReservationService {

    void loadReservations(Integer id, Model model) throws ServiceException;

    void cancelReservation(Integer id) throws ServiceException;

    void approve(Integer reservationId, Integer roomId) throws ServiceException;

    void pay(Integer id, String cardNumber, String cvv, String validThru) throws ServiceException;

    void checkIn(Integer id) throws ServiceException;

    void checkOut(Integer id) throws ServiceException;

}