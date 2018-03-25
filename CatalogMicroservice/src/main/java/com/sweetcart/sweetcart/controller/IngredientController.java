package com.sweetcart.sweetcart.controller;

import com.sweetcart.sweetcart.entity.Ingredient;
import com.sweetcart.sweetcart.entity.Request.AddIngredientRequest;
import com.sweetcart.sweetcart.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ingredient> findAllIngredients ()
    {
        return ingredientRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addIngredient (@RequestBody AddIngredientRequest addIngredientRequest)
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(addIngredientRequest.getName());
        ingredientRepository.save(ingredient);
    }
}

