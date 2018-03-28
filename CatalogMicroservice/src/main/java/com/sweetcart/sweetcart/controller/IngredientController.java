package com.sweetcart.sweetcart.controller;

import com.sweetcart.sweetcart.entity.CakeShop;
import com.sweetcart.sweetcart.entity.Ingredient;
import com.sweetcart.sweetcart.entity.Request.AddIngredientRequest;
import com.sweetcart.sweetcart.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
    /*//ALL
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ingredient> findAllIngredients ()
    {
        return ingredientRepository.findAll();
    }

    //ADD
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addIngredient (@RequestBody AddIngredientRequest addIngredientRequest)
    {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(addIngredientRequest.getName());
        ingredientRepository.save(ingredient);
    }*/

    // GET ALL
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Ingredient>> findAll() {
        Collection<Ingredient> clients = this.ingredientRepository.findAll();
        if(clients.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Ingredient>>(clients, HttpStatus.OK);
    }

    // RETRIEVE ONE
    @RequestMapping(method = RequestMethod.GET, value = "/{ingredientId}")
    ResponseEntity<?> getIngredient (@PathVariable Long ingredientId) {

        Optional<Ingredient> client = this.ingredientRepository.findById(ingredientId);
        if (!client.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Ingredient>>(client, HttpStatus.OK);
    }

    //CREATE NEW
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@Valid @RequestBody Ingredient ingredient, UriComponentsBuilder ucBuilder) {

        Collection<Ingredient> offers = this.ingredientRepository.findAll();
        boolean exists = false;
        for (Iterator<Ingredient> i = offers.iterator(); i.hasNext();) {
            if(i.next().getName().equals(ingredient.getName()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A Ingredient with name " + ingredient.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        ingredientRepository.save(ingredient);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/ingredients/{id}").buildAndExpand(ingredient.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //EDIT
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editIngredient(@PathVariable("id") long id, @Valid @RequestBody Ingredient ingredient) {

        Optional<Ingredient> currentIngredient = ingredientRepository.findById(id);

        if (!currentIngredient.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Ingredient with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentIngredient.get().setName(ingredient.getName());
        currentIngredient.get().setOffers(ingredient.getOffers());

        ingredientRepository.save(currentIngredient.get());
        return new ResponseEntity<Optional<Ingredient>>(currentIngredient, HttpStatus.OK);
    }

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") long id) {

        Optional<Ingredient> country =ingredientRepository.findById(id);
        if (!country.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Ingredient with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        ingredientRepository.deleteById(id);
        return new ResponseEntity<Ingredient>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Ingredient> deleteAll() {

        ingredientRepository.deleteAll();
        return new ResponseEntity<Ingredient>(HttpStatus.NO_CONTENT);
    }
}

