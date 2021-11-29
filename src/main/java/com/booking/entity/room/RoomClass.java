package com.booking.entity.room;

import com.booking.entity.Identifiable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "room_class")
public class RoomClass implements Identifiable, Serializable {

    private static final long serialVersionUID = -3864008028644659221L;

    @Id
    private Integer id;

    @Column(name = "class_name")
    private String name;

    @Column(name = "basic_rate")
    private BigDecimal basicRate;

    @Column(name = "rate_per_person")
    private BigDecimal ratePerPerson;

    public RoomClass() {

    }

    public RoomClass(String name, BigDecimal basicRate, BigDecimal ratePerPerson) {
        this.name = name;
        this.basicRate = basicRate;
        this.ratePerPerson = ratePerPerson;
    }

    public RoomClass(Integer id, String name, BigDecimal basicRate, BigDecimal ratePerPerson) {
        this(name, basicRate, ratePerPerson);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }

    public BigDecimal getRatePerPerson() {
        return ratePerPerson;
    }

    public void setRatePerPerson(BigDecimal ratePerPerson) {
        this.ratePerPerson = ratePerPerson;
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
        RoomClass other = (RoomClass) obj;
        return (id != null) ? id.equals(other.id) : other.id == null;
    }

    @Override
    public int hashCode() {
        return ((id != null) ? id.hashCode() : 0) +
                ((name != null) ? name.hashCode() : 0) +
                ((basicRate != null) ? 13 * basicRate.hashCode() : 0) +
                ((ratePerPerson != null) ? 19 * ratePerPerson.hashCode() : 0);
    }

}
