package com.epam.booking.utils;

import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RoomPicker {

    private DateUtils dateUtils;

    public RoomPicker(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    public List<Room> getSuitableRooms(List<Room> rooms, List<Reservation> reservations, Reservation reservation) {
        RoomClass preferredRoomClass = reservation.getRoomClass();
        int personsAmount = reservation.getPersonsAmount();
        Date arrivalDate = reservation.getArrivalDate();
        Date departureDate = reservation.getDepartureDate();

        return rooms.stream()
                .filter(r -> r.isActive() &&
                        r.getBedsAmount() == personsAmount &&
                        r.getRoomClass().equals(preferredRoomClass) &&
                        isRoomFree(r, arrivalDate, departureDate, reservations))
                .collect(Collectors.toList());
    }

    private boolean isRoomFree(Room room, Date arrivalDate, Date departureDate, List<Reservation> reservations) {
        return reservations.stream()
                .filter(r ->
                        r.getReservationStatus() != ReservationStatus.CHECKED_OUT &&
                        r.getReservationStatus() != ReservationStatus.CANCELLED &&
                        r.getRoom() != null && r.getRoom().equals(room))
                .noneMatch(r -> dateUtils.isBetweenDates(arrivalDate, r.getArrivalDate(), r.getDepartureDate()) &&
                        dateUtils.isBetweenDates(departureDate, r.getArrivalDate(), r.getDepartureDate()));
    }

}
