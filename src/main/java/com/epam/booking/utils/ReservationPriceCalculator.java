package com.epam.booking.utils;

import web.entity.reservation.Reservation;
import web.entity.room.RoomClass;
import java.math.BigDecimal;
import java.util.Date;

public class ReservationPriceCalculator {

    private static final DateUtils DATE_UTILS = new DateUtils();
    private static final PriceCalculator PRICE_CALCULATOR = new PriceCalculator();

    public static BigDecimal calculateReservationPrice(Reservation reservation) {
        Date arrivalDate = reservation.getArrivalDate();
        Date departureDate = reservation.getDepartureDate();
        int days = DATE_UTILS.calculateDaysBetweenDates(arrivalDate, departureDate);
        RoomClass roomClass = reservation.getRoomClass();
        int personsAmount = reservation.getPersonsAmount();
        return PRICE_CALCULATOR.calculateTotalPrice(days, roomClass, personsAmount);
    }

}
