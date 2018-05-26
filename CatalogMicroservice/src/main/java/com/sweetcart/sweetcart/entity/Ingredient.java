package com.sweetcart.sweetcart.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class,property="@id", scope = Ingredient.class)
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

    @Override
    public String toString(){
        JSONObject jsonInfo = new JSONObject();

        try {
            jsonInfo.put("id", this.id);
            jsonInfo.put("name", this.name);

        } catch (JSONException e1) {}

        return jsonInfo.toString();
    }
}
