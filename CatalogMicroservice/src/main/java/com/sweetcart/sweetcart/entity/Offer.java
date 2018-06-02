package com.sweetcart.sweetcart.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Offer.class)
public class Offer {
    @Id
    @GeneratedValue

    private Long id;

    @NotNull(message = "Name must be input")
    @Size(min=1, max=60, message = "size between 1 and 60")
    private String name;

    @NotNull(message = "Category must be input")
    @Size(min=1, max=60, message = "size between 1 and 60")
    private String category;

    @NotNull(message = "Description must be input")
    @Size(min=1, max=255, message = "size between 1 and 255")
    private String description;

    @NotNull(message = "Picture must be input")
    @Size(min=1, max=1000, message = "size between 1 and 1000")
    private String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String picture) {
        this.description = description;
    }

    private double avg_review;

    @NotNull(message = "Price must be input")
    private double price;

    @ManyToOne(targetEntity = CakeShop.class)
    @JoinColumn(name = "cake_shop_id")
    private CakeShop cakeShopId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public CakeShop getCakeShopId() {
        return cakeShopId;
    }

    public void setCakeShopId(CakeShop cakeShopId) {
        this.cakeShopId = cakeShopId;
    }


    @Override
    public String toString(){
        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("id", this.id);
            jsonInfo.put("name", this.name);
            jsonInfo.put("category", this.category);
            jsonInfo.put("avg_review", this.avg_review);
            jsonInfo.put("price", this.price);
            jsonInfo.put("cake_shopid", this.cakeShopId.getId());
            jsonInfo.put("picture", this.picture);
            jsonInfo.put("description", this.description);


        } catch (JSONException e1) {}
        return jsonInfo.toString();
    }
}
