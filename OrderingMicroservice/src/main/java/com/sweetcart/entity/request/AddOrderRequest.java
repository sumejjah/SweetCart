package com.sweetcart.entity.request;

import com.sweetcart.entity.Client;
import com.sweetcart.entity.Offer;

/**
 * Created by Sumejja on 23.03.2018..
 */
public class AddOrderRequest {

    private String adress;
    private long telephone;
    private Client client;
    private Offer offer;


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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offerid) {
        this.offer = offer;
    }
}
