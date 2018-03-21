package com.sweetcart.controller;

import com.sweetcart.entity.Ingredient;
import com.sweetcart.entity.request.AddIngredientRequest;
import com.sweetcart.repository.IngredientRepository;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Ingredient> findAllIngredients ()
    {
        return ingredientRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addIngredient (@RequestBody AddIngredientRequest addIngredientRequest)
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(addIngredientRequest.getName());
        ingredientRepository.save(ingredient);
    }
}
