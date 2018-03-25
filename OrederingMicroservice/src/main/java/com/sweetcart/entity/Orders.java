package com.sweetcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Sumejja on 23.03.2018..
 */
@Entity
public class Orders {

    @Id
    @GeneratedValue
    private long id;

    private String adress;
    private long telephone;
    private long clientid;
    private long offerid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
