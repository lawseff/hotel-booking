package web.utils;

import org.springframework.stereotype.Component;
import web.entity.reservation.Reservation;
import web.entity.reservation.ReservationStatus;
import web.entity.room.Room;
import web.entity.room.RoomClass;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomUtils {

    private DateUtils dateUtils;

    public RoomUtils(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    public List<Room> getAvailableRooms(List<Room> rooms, List<Reservation> reservations, Reservation reservation) {
        Date arrivalDate = reservation.getArrivalDate();
        Date departureDate = reservation.getDepartureDate();

        return rooms.stream()
                .filter(r -> r.isActive() &&
                        isRoomSuitable(r, reservation) &&
                        isRoomFree(r, arrivalDate, departureDate, reservations))
                .collect(Collectors.toList());
    }

    public boolean isRoomSuitable(Room room, Reservation reservation) {
        RoomClass preferredRoomClass = reservation.getRoomClass();
        RoomClass roomClass = room.getRoomClass();
        return room.getBedsAmount() == reservation.getPersonsAmount() && roomClass.equals(preferredRoomClass);
    }

    public boolean isRoomFree(Room room, Date arrivalDate, Date departureDate, List<Reservation> allReservations) {
        return allReservations.stream()
                .filter(r ->
                        r.getReservationStatus() != ReservationStatus.CHECKED_OUT &&
                        r.getReservationStatus() != ReservationStatus.CANCELLED &&
                        r.getRoom() != null && r.getRoom().equals(room))
                .noneMatch(r -> dateUtils.isBetweenDates(arrivalDate, r.getArrivalDate(), r.getDepartureDate()) &&
                        dateUtils.isBetweenDates(departureDate, r.getArrivalDate(), r.getDepartureDate()));
    }

}
