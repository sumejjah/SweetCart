package com.sweetcart.entity.request;

/**
 * Created by Sumejja on 23.03.2018..
 */
public class AddOrderRequest {

    private String adress;
    private long telephone;
    private long clientid;
    private long offerid;

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
