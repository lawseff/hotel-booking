package web.entity.room;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import web.entity.Identifiable;
import java.io.Serializable;

@Entity
@Table(name = "room")
public class Room implements Identifiable, Serializable {

    private static final long serialVersionUID = -1947754420403223291L;

    @Id
    private Integer id;

    @Column(name = "is_active")
    private boolean active;

    @OneToOne
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    @Column(name = "beds_amount")
    private int bedsAmount;

    public Room() {

    }

    public Room(Integer id, boolean active, RoomClass roomClass, int bedsAmount) {
        this.active = active;
        this.roomClass = roomClass;
        this.bedsAmount = bedsAmount;
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public int getBedsAmount() {
        return bedsAmount;
    }

    public void setBedsAmount(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Room other = (Room) obj;
        return (id != null) ? id.equals(other.id) : other.id == null;
    }

    @Override
    public int hashCode() {
        return ((id != null) ? id.hashCode() : 0) +
                Boolean.hashCode(active) +
                ((roomClass != null) ? roomClass.hashCode() : 0) +
                Integer.hashCode(bedsAmount);
    }

}
