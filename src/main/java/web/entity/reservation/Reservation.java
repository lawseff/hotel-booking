package web.entity.reservation;

import web.entity.Identifiable;
import web.entity.User;
import web.entity.room.Room;
import web.entity.room.RoomClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Reservation implements Identifiable, Serializable {

    private static final long serialVersionUID = 4646050451695840394L;
    private Integer id;
    private User user;
    private RoomClass roomClass;
    private Room room;
    private ReservationStatus reservationStatus;
    private Date arrivalDate;
    private Date departureDate;
    private int personsAmount;
    private BigDecimal totalPrice;

    public Reservation() {

    }

    public Reservation(User user,
                       RoomClass roomClass, Room room,
                       ReservationStatus reservationStatus,
                       Date arrivalDate, Date departureDate,
                       int personsAmount, BigDecimal totalPrice) {
        this.user = user;
        this.roomClass = roomClass;
        this.room = room;
        this.reservationStatus = reservationStatus;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.personsAmount = personsAmount;
        this.totalPrice = totalPrice;
    }

    public Reservation(Integer id, User user,
                       RoomClass roomClass, Room room,
                       ReservationStatus reservationStatus,
                       Date arrivalDate, Date departureDate,
                       int personsAmount, BigDecimal totalPrice) {
        this(user, roomClass, room, reservationStatus, arrivalDate, departureDate, personsAmount, totalPrice);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public int getPersonsAmount() {
        return personsAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
