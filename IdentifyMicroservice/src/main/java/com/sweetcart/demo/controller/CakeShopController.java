package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.CakeShop;

import com.sweetcart.demo.entity.Client;
import com.sweetcart.demo.entity.User;
import com.sweetcart.demo.entity.request.AddCakeShopRequest;
import com.sweetcart.demo.repository.CakeShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cakeshops")
@CrossOrigin(origins = "http://localhost:3000")
public class CakeShopController {
    private CakeShopRepository cakeShopRepository;

    @Autowired
    public CakeShopController(CakeShopRepository cakeShopRepository) {
        this.cakeShopRepository = cakeShopRepository;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    public void addCakeShopOrderMS(Long cake_shopId) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("CatalogMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/cakeshops/addCakeShop/" + cake_shopId.toString();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
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
    ResponseEntity<?> getCakeShop(@PathVariable Long cakeShopId) {

        Optional< CakeShop>  cakeShop = this.cakeShopRepository.findById(cakeShopId);
        if (!cakeShop.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional< CakeShop>>(cakeShop, HttpStatus.OK);
    }

    //RETRIEVE ONE CAKE SHOP METHOD
    CakeShop getCakeShopByID(Long cakeShopId) {

        Collection<CakeShop> cakeShops = this.cakeShopRepository.findAll();
        CakeShop cakeShop = new CakeShop();
        boolean exists = false;
        for (CakeShop u: cakeShops) {
            if(u.getId().equals(cakeShopId)){
                exists = true;
                cakeShop = u;
                System.out.println("postoji i naso sam");
            }
        }

        if (!exists) {
            System.out.println("nisam naso ");
            return null;
        }


        return cakeShop;
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

        try {
            addCakeShopOrderMS(getCakeShopByName(cakeShop.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/cakeshops/{id}").buildAndExpand(cakeShop.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    Long getCakeShopByName (String firstName){

        Collection<CakeShop> cakeShops = this.cakeShopRepository.findAll();
        CakeShop cakeShop = new CakeShop();
        boolean exists = false;
        for (CakeShop u: cakeShops) {
            if(u.getName().equals(firstName)){
                exists = true;
                cakeShop = u;
            }
        }
        if (!exists)
            return null;

        return cakeShop.getId();
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

        try {
            deleteCakeShopOrderMS(cakeShop.getName());
            addCakeShopOrderMS(getCakeShopByName(currentCakeShop.get().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Optional<CakeShop>>(currentCakeShop, HttpStatus.OK);
    }

    public void deleteCakeShopOrderMS(String cake_shopName) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("CatalogMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/cakeshops/name/" + cake_shopName;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl, HttpMethod.DELETE, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCakeShop(@PathVariable("id") long id) {

        Optional<CakeShop> cakeShop = cakeShopRepository.findById(id);
        if (!cakeShop.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Cake shop with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        try {
            System.out.println(getCakeShopByID(id).getName());
            deleteCakeShopOrderMS(getCakeShopByID(id).getName());
        } catch (IOException e) {
            e.printStackTrace();
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


}
