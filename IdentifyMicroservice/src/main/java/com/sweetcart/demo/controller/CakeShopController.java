package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.CakeShop;

import com.sweetcart.demo.entity.request.AddCakeShopRequest;
import com.sweetcart.demo.repository.CakeShopRepository;
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
@RequestMapping("/cakeshops")
public class CakeShopController {
    private CakeShopRepository cakeShopRepository;

    @Autowired
    public CakeShopController(CakeShopRepository cakeShopRepository) {
        this.cakeShopRepository = cakeShopRepository;
    }


    // GET ALL CAKE SHOPS
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<CakeShop>> findAll() {
        Collection<CakeShop> cakeShops = this.cakeShopRepository.findAll();
        if(cakeShops.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<CakeShop>>(cakeShops, HttpStatus.OK);
    }

    // RETRIEVE ONE CAKE SHOP
    @RequestMapping(method = RequestMethod.GET, value = "/{cakeShopId}")
    ResponseEntity<?> getCakeShop(@PathVariable Long userId) {

        Optional< CakeShop>  cakeShop = this.cakeShopRepository.findById(userId);
        if (!cakeShop.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional< CakeShop>>(cakeShop, HttpStatus.OK);
    }
    //CREATE NEW CAKE SHOP
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createCakeShop(@Valid @RequestBody  CakeShop cakeShop, UriComponentsBuilder ucBuilder) {

        Collection< CakeShop>  cakeShops = this.cakeShopRepository.findAll();
        boolean exists = false;
        for (Iterator<CakeShop> i = cakeShops.iterator(); i.hasNext();) {
            if(i.next().getName().equals(cakeShop.getName()))
                exists = true;
            int x = 5;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A CakeShop with name " + cakeShop.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        cakeShopRepository.save(cakeShop);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cakeshops/{id}").buildAndExpand(cakeShop.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE CAKE SHOP
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCakeShop(@PathVariable("id") long id,@Valid @RequestBody CakeShop cakeShop) {

        Optional<CakeShop> currentCakeShop = cakeShopRepository.findById(id);

        if (!currentCakeShop.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Cake shop with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentCakeShop.get().setName(cakeShop.getName());
        currentCakeShop.get().setAddress(cakeShop.getAddress());
        currentCakeShop.get().setDescription(cakeShop.getDescription());
        currentCakeShop.get().setUserId(cakeShop.getUserId());

        cakeShopRepository.save(currentCakeShop.get());
        return new ResponseEntity<Optional<CakeShop>>(currentCakeShop, HttpStatus.OK);
    }
    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCakeShop(@PathVariable("id") long id) {

        Optional<CakeShop> cakeShop = cakeShopRepository.findById(id);
        if (!cakeShop.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Cake shop with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
       cakeShopRepository.deleteById(id);
        return new ResponseEntity<CakeShop>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<CakeShop> deleteAll() {

        cakeShopRepository.deleteAll();
        return new ResponseEntity<CakeShop>(HttpStatus.NO_CONTENT);
    }

    /*@RequestMapping(value="/all",method= RequestMethod.GET)
    public List<CakeShop> findAllCakeShops(){
        return cakeShopRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addCakeShop(@RequestBody AddCakeShopRequest addCakeShopRequest){
        CakeShop cakeShop=new CakeShop();
        cakeShop.setName(addCakeShopRequest.getName());
        cakeShop.setAddress(addCakeShopRequest.getAddress());
        cakeShop.setDescription(addCakeShopRequest.getDescription());
        cakeShop.setUserId(addCakeShopRequest.getUserId());

       cakeShopRepository.save(cakeShop);

    }
    */
}
