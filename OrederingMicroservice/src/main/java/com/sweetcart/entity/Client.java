package com.sweetcart.entity;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue
    private long id;
    private int bonus;
    private String firstName;
    private String lastName;
    private long userid;


    public Client() {
    }

    public Client(long id, int bonus, String firstName, String lastName, long userid) {
        this.id = id;
        this.bonus = bonus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userid = userid;

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
