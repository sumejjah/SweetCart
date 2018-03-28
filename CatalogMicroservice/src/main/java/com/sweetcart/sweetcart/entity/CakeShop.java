package com.sweetcart.sweetcart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CakeShop implements Serializable{
    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "Name must be input")
    @Size(min=1, max=60, message = "size between 1 and 60")
    private String name;

    @NotNull
    @Size(min=1, max=60, message = "size between 1 and 60")
    private String address;

    @Size(min=1, max=255, message = "size between 1 and 255")
    private String description;

    //@NotNull
    //@Size(min=1, max=10, message = "size between 1 and 10")
    private long userId;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


}