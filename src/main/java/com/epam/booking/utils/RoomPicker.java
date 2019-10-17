package com.epam.booking.utils;

import com.epam.booking.entity.reservation.Reservation;
import com.epam.booking.entity.reservation.ReservationStatus;
import com.epam.booking.entity.room.Room;
import com.epam.booking.entity.room.RoomClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        List<Room> suitableRooms = new ArrayList<>();

        for (Room room : rooms) {
            RoomClass roomClass = room.getRoomClass();
            if (room.getBedsAmount() == personsAmount && roomClass.equals(preferredRoomClass) &&
                    isRoomFree(room, arrivalDate, departureDate, reservations) && room.isActive()) {
                suitableRooms.add(room);
            }
        }
        return suitableRooms;
    }

    // TODO lambda
    private boolean isRoomFree(Room room, Date arrivalDate, Date departureDate, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            ReservationStatus status = reservation.getReservationStatus();
            if (status == ReservationStatus.CHECKED_OUT || status == ReservationStatus.CANCELLED) {
                continue;
            }
            Room potentialRoom = reservation.getRoom();
            if (potentialRoom != null && potentialRoom.equals(room)) {
                Date reservationArrivalDate = reservation.getArrivalDate();
                Date reservationDepartureDate = reservation.getDepartureDate();
                if (dateUtils.isBetweenDates(arrivalDate, reservationArrivalDate, reservationDepartureDate) ||
                        dateUtils.isBetweenDates(departureDate, reservationArrivalDate, reservationDepartureDate)) {
                    return false;
                }
            }
        }
        return true;
    }

}
