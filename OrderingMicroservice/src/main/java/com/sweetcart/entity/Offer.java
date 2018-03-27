package com.sweetcart.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Offer implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "name must be input") @Size(min = 2, max = 60, message = "min 2, max 60 elements")
    private String name;

    @NotNull@NotNull(message = "category must be input") @Size(min = 2, max = 60, message = "min 2, max 60 elements")
    private String category;

    @NotNull(message = "cake_shopid must be input")
    private long cake_shopid;

    private double avg_review;

    @NotNull(message = "price must be input")
    private double price;

    public Offer() {
    }

    public Offer(@NotNull(message = "name must be input") @Size(min = 2, max = 60, message = "min 2, max 60 elements") String name, @NotNull @NotNull(message = "category must be input") @Size(min = 2, max = 60, message = "min 2, max 60 elements") String category, @NotNull(message = "cake_shopid must be input") long cake_shopid, double avg_review, @NotNull(message = "price must be input") double price) {
        this.name = name;
        this.category = category;
        this.cake_shopid = cake_shopid;
        this.avg_review = avg_review;
        this.price = price;
    }

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