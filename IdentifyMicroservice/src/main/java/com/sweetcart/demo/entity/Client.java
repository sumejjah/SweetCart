package com.sweetcart.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Client {


    @Id
    @GeneratedValue
    private Long id;


    @Max(10)
    private Integer bonus;

    @NotNull
    @Size(min=1,max=60)
    private String firstName;

    public Client() {
    }

    @NotNull
    @Size(min=1,max=60)

    private String lastName;

    public Client(@Max(10) Integer bonus, @NotNull @Size(min = 1, max = 60) String firstName, @NotNull @Size(min = 1, max = 60) String lastName, User userId) {
        this.bonus = bonus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
