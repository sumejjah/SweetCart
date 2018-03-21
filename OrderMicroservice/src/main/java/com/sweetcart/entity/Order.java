package com.sweetcart.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order{

    @Id
    @GeneratedValue
    private long id;
    @Column(name="client_id")
    private long clientid;

    @Column(name = "offer_id")
    private long offerid;

    @Column(name = "adress")
    private String adress;

    @Column(name = "telephone")
    private long telephone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientid() {
        return clientid;
    }

    public void setClientid(long clientid) {
        this.clientid = clientid;
    }

    public long getOfferid() {
        return offerid;
    }

    public void setOfferid(long offerid) {
        this.offerid = offerid;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

}
