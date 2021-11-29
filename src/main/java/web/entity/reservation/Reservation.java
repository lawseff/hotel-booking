package web.entity.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import web.entity.Identifiable;
import web.entity.User;
import web.entity.room.Room;
import web.entity.room.RoomClass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation implements Identifiable, Serializable {

    private static final long serialVersionUID = 4646050451695840394L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "reservation_status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "persons_amount")
    private int personsAmount;

    @Column(name = "total_price")
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setPersonsAmount(int personsAmount) {
        this.personsAmount = personsAmount;
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
