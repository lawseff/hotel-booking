package com.epam.booking.entity.room;

import com.epam.booking.entity.Identifiable;
import java.io.Serializable;
import java.math.BigDecimal;

public class RoomClass implements Identifiable, Serializable {

    private static final long serialVersionUID = -3864008028644659221L;
    private Integer id;
    private String name;
    private BigDecimal basicRate;
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

    public String getName() {
        return name;
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
