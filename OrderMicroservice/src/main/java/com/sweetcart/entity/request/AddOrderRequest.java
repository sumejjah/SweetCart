package com.sweetcart.entity.request;

/**
 * Created by Sumejja on 20.03.2018..
 */
public class AddOrderRequest {
    private long clientid;
    private long offerid;
    private String adress;
    private long telephone;

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
