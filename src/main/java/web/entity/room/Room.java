package web.entity.room;

import web.entity.Identifiable;
import java.io.Serializable;

public class Room implements Identifiable, Serializable {

    private static final long serialVersionUID = -1947754420403223291L;
    private Integer id;
    private boolean active;
    private RoomClass roomClass;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public int getBedsAmount() {
        return bedsAmount;
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
