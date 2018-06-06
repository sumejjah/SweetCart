package com.sweetcart.entity.request;

public class AddOfferRequest {
    private String name;
    private String category;
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private long cake_shopid;
    private double avg_review;
    private double price;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
