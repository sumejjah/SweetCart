package com.sweetcart.sweetcart.entity.Request;

public class AddOfferRequest {

    private String name;

    private String category;

    private long cake_shopid;

    private double avg_review;

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

    private double price;
}