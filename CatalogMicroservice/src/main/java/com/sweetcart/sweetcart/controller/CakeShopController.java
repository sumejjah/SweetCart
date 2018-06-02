package com.sweetcart.sweetcart.controller;

import com.sweetcart.sweetcart.entity.CakeShop;
import com.sweetcart.sweetcart.entity.Request.AddCakeShopRequest;
import com.sweetcart.sweetcart.repository.CakeShopRepository;
import org.json.JSONObject;
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

    public CakeShopController(){}

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(method = RequestMethod.GET, value = "addCakeShop/{cake_shopId}")
    public void addCakeShop(@PathVariable Long cake_shopId) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("IdentifyMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/cakeshops/" + cake_shopId.toString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        String dobiveni = response.getBody();
        JSONObject jsonObject = new JSONObject(dobiveni);
        Iterator<String> it = jsonObject.keys();
        CakeShop cakeShop = new CakeShop();

        while(it.hasNext()){
            String key = it.next();
            if(key.equals("name"))
                cakeShop.setName(jsonObject.getString("name"));
            else if(key.equals("address"))
                cakeShop.setAddress(jsonObject.getString("address"));
            else if(key.equals("id"))
                cakeShop.setId(jsonObject.getLong("id"));
            else if(key.equals("description"))
                cakeShop.setDescription(jsonObject.getString("description"));
            else if (key.equals("userId")){
                JSONObject sub = (JSONObject) jsonObject.get(key);
                cakeShop.setUserId(sub.getLong("id"));
            }

        }

        createCakeShopIdentify(cakeShop);
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


    private CakeShopRepository cakeShopRepository;

    //ALL
    @Autowired
    public CakeShopController(CakeShopRepository cakeShopRepository) {
        this.cakeShopRepository = cakeShopRepository;
    }

    //ALL
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<CakeShop>> findAll() {
        Collection<CakeShop> shops = this.cakeShopRepository.findAll();
        if(shops.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<CakeShop>>(shops, HttpStatus.OK);
    }

    // RETRIEVE ONE
    @RequestMapping(method = RequestMethod.GET, value = "/{cakeshopId}")
    ResponseEntity<?> getCakeShop (@PathVariable Long cakeshopId) {

        Optional<CakeShop> cakeShop = this.cakeShopRepository.findById(cakeshopId);
        if (!cakeShop.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<CakeShop>>(cakeShop, HttpStatus.OK);
    }

    // RETRIEVE ONE CAKE SHOP BY NAME
    @RequestMapping(method = RequestMethod.DELETE, value = "/name/{userName}")
    ResponseEntity<?> deleteByName (@PathVariable String userName) {

        Collection<CakeShop> cakeShops = this.cakeShopRepository.findAll();
        CakeShop cakeShop = new CakeShop();
        boolean exists = false;
        for (CakeShop u: cakeShops) {
            if(u.getName().equals(userName)){
                exists = true;
                cakeShop = u;
            }
        }

        if (!exists) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. CakeShop with name " + userName + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        cakeShopRepository.deleteById(cakeShop.getId());
        return new ResponseEntity<CakeShop>(HttpStatus.NO_CONTENT);
    }

    //CREATE NEW
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createCakeShop(@Valid @RequestBody CakeShop cakeShop, UriComponentsBuilder ucBuilder) {

        Collection<CakeShop> clients = this.cakeShopRepository.findAll();
        boolean exists = false;
        for (Iterator<CakeShop> i = clients.iterator(); i.hasNext();) {
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

    //EDIT
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editCakeShop(@PathVariable("id") long id, @Valid @RequestBody CakeShop cakeShop) {

        Optional<CakeShop> currentCakeShop = cakeShopRepository.findById(id);

        if (!currentCakeShop.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Cake Shop with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentCakeShop.get().setName(cakeShop.getName());
        currentCakeShop.get().setAddress(cakeShop.getAddress());
        currentCakeShop.get().setDescription(cakeShop.getDescription());
        currentCakeShop.get().setUserId(cakeShop.getUserId());

        cakeShopRepository.save(currentCakeShop.get());
        return new ResponseEntity<Optional<CakeShop>>(currentCakeShop, HttpStatus.OK);
    }

    //CREATE NEW CLIENT - samo za komunikaciju između identify i order
    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public boolean createCakeShopIdentify(CakeShop cakeShop) {

        Collection<CakeShop> cakeShops = this.cakeShopRepository.findAll();
        boolean exists = false;
        for (Iterator<CakeShop> i = cakeShops.iterator(); i.hasNext();) {
            if(i.next().getName().equals(cakeShop.getName()))
                exists = true;
        }

        if (exists) {
            return false;
        }
        cakeShopRepository.save(cakeShop);

        return true;
    }

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCakeShop(@PathVariable("id") long id) {

        Optional<CakeShop> country =cakeShopRepository.findById(id);
        if (!country.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. CakeShop with id " + id + " not found."),
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
}
