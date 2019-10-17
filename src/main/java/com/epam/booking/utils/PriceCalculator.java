package com.epam.booking.utils;

import com.epam.booking.entity.room.RoomClass;
import java.math.BigDecimal;

public class PriceCalculator {

    public BigDecimal calculateTotalPrice(int days, RoomClass roomClass, int personsAmount) {
        BigDecimal ratePerPerson = roomClass.getRatePerPerson();
        BigDecimal personsMultiplier = new BigDecimal(personsAmount);
        BigDecimal priceForPersons = ratePerPerson.multiply(personsMultiplier);

        BigDecimal basicRate = roomClass.getBasicRate();
        BigDecimal pricePerDay = basicRate.add(priceForPersons);

        BigDecimal daysMultiplier = new BigDecimal(days);
        return pricePerDay.multiply(daysMultiplier);
    }

}
