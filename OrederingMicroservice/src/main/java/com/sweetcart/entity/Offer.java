package com.sweetcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Offer {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String category;
    private long cake_shopid;
    private double avg_review;
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getCake_shopid() {
        return cake_shopid;
    }

    public void setCake_shopid(long cake_shopid) {
        this.cake_shopid = cake_shopid;
    }

    public double getAvg_review() {
        return avg_review;
    }

    public void setAvg_review(double avg_review) {
        this.avg_review = avg_review;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
