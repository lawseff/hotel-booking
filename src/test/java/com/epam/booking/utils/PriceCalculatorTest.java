package com.epam.booking.utils;

import com.epam.booking.entity.room.RoomClass;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.math.BigDecimal;

@RunWith(DataProviderRunner.class)
public class PriceCalculatorTest {

    private static final RoomClass STANDARD = new RoomClass("STANDARD",
            new BigDecimal("85.00"), // basic rate
            new BigDecimal("15.00") // rate per person
    );

    private static final RoomClass LUXE = new RoomClass("LUXE",
            new BigDecimal("120.00"),
            new BigDecimal("20.00")
    );

    private static final RoomClass VIP = new RoomClass("VIP",
            new BigDecimal("145.00"),
            new BigDecimal("25.00")
    );

    private PriceCalculator calculator = new PriceCalculator();

    @DataProvider
    public static Object[][] dataProviderCalculatePrice() {
        return new Object[][] {
                { 4, STANDARD, 3, new BigDecimal("520.00") }, // days, roomClass, personsAmount, expectedPrice
                { 1, LUXE, 2, new BigDecimal("160.00") },
                { 10, VIP, 1, new BigDecimal("1700.00") }
        };
    }

    @Test
    @UseDataProvider("dataProviderCalculatePrice")
    public void calculatePrice_OrderParams_BigDecimal(
            int days, RoomClass roomClass, int personsAmount, BigDecimal expectedPrice) {
        // given

        // when
        BigDecimal price = calculator.calculateTotalPrice(days, roomClass, personsAmount);

        // then
        Assert.assertEquals(expectedPrice, price);
    }

}
