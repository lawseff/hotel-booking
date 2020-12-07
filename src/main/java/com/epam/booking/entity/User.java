package com.epam.booking.entity;

import java.io.Serializable;

public class User implements Identifiable, Serializable {

    private static final long serialVersionUID = 5113246202178236722L;
    private Integer id;
    private boolean admin;
    private String email;
    private String password;
    private String firstName;
    private String secondName;

    public User() {

    }

    public User(boolean admin, String email, String password, String firstName, String secondName) {
        this.admin = admin;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public User(Integer id, boolean admin, String email, String password, String firstName, String secondName) {
        this(admin, email, password, firstName, secondName);
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return id + "";
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
        User other = (User) obj;
        return (id != null) ? id.equals(other.id) : other.id == null;
    }

    @Override
    public int hashCode() {
        return ((id != null) ? id.hashCode() : 0) +
                Boolean.hashCode(admin) +
                ((email != null) ? email.hashCode() : 0) +
                ((password != null) ? password.hashCode() : 0) +
                ((firstName != null) ? firstName.hashCode() : 0) +
                ((secondName != null) ? secondName.hashCode() : 0);
    }

}
