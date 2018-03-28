package com.sweetcart.sweetcart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Name must be input")
    @Size(min=1, max=60, message = "size between 1 and 60")
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Offer> offers;

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

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}
