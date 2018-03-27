package com.sweetcart.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CakeShop {
    @NotNull
    @Max(10)
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min=1,max=60)
    private String name;

    @NotNull
    @Size(min=1,max=60)
    private String address;



    @Size(max=255)
    private String description;

    @NotNull
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;



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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
