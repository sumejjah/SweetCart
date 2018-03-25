package com.sweetcart.entity;

import javax.persistence.*;
import java.util.Set;

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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="offer_id")
    private Offer offer;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "orders_requirement", joinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "requirement_id", referencedColumnName = "id"))

    private Set<Requirement> requirements;

    public Orders() {
    }

    public Orders(long id, String adress, long telephone, Client client, Offer offer, Set<Requirement> requirements ) {
        this.id = id;
        this.adress = adress;
        this.telephone = telephone;
        this.client = client;
        this.offer = offer;
        this.requirements=requirements;
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


    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }
}
