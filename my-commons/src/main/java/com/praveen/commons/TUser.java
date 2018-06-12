package com.praveen.commons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.praveen.commons.utils.ToStringUtils;

@Entity
@Table(name = "T_USER_AUTH")
public class TUser implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "UID")
    private int userId;
    @Column(name = "ACTIVE")
    private boolean status;
    @Id
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return ToStringUtils.asString(this, "userId", "status", "email", "firstName");
    }

}
