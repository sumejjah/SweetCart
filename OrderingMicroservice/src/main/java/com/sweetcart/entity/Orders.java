package com.sweetcart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Sumejja on 23.03.2018..
 */
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "adress must be input")
    private String adress;
    private long telephone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="offer_id")
    private Offer offer;

    public Orders() {
    }

    public Orders(long id, String adress, long telephone, Client client, Offer offer, Set<Requirement> requirements ) {
        this.id = id;
        this.adress = adress;
        this.telephone = telephone;
        this.client = client;
        this.offer = offer;
    }

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


    public  Client getClient() {
        return client;
    }

    public void setClient(Client client) { this.client = client; }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offerid) {
        this.offer= offer;
    }


}
