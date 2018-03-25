package com.sweetcart.sweetcart.entity.Request;

import org.springframework.web.bind.annotation.RequestMapping;


public class AddIngredientRequest {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
