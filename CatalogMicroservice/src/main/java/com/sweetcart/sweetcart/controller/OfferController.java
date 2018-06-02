package com.sweetcart.sweetcart.controller;

import com.sweetcart.sweetcart.entity.Offer;
import com.sweetcart.sweetcart.entity.Producer;
import com.sweetcart.sweetcart.repository.OfferRepository;
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
@RequestMapping("/offers")
@CrossOrigin(origins = "http://localhost:3000")
public class OfferController {
    @Autowired
    Producer producer;

    @Autowired
    private DiscoveryClient discoveryClient;

    //@RequestMapping(method = RequestMethod.GET, value = "deleteOffer/{offerName}")
    public void deleteOfferAsync(String offerName) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("OrderingMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/offer/name/" + offerName;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl,
                    HttpMethod.DELETE, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }

        //System.out.println(response.getBody());
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/send/{offerId}")
    public String sendMsg(@PathVariable Long offerId){

        Optional<Offer> client = this.offerRepository.findById(offerId);
        if (client == null) {
            System.out.println("nij epornasao");
            return "ne postoji";
        }

        System.out.println("NASAO");
        producer.produceMsg(client.get().toString());

        return "uspješno";
    }

    private OfferRepository offerRepository;

    @Autowired
    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    // GET ALL
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Offer>> findAll() {
        Collection<Offer> clients = this.offerRepository.findAll();
        if(clients.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Offer>>(clients, HttpStatus.OK);
    }

    // RETRIEVE ONE
    @RequestMapping(method = RequestMethod.GET, value = "/{offerId}")
    ResponseEntity<?> getOffer (@PathVariable Long offerId) {

        Optional<Offer> client = this.offerRepository.findById(offerId);
        if (!client.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Offer>>(client, HttpStatus.OK);
    }

    //CREATE NEW
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer, UriComponentsBuilder ucBuilder) {

        Collection<Offer> offers = this.offerRepository.findAll();
        boolean exists = false;
        for (Iterator<Offer> i = offers.iterator(); i.hasNext();) {
            if(i.next().getName().equals(offer.getName()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A Offer with name " + offer.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        offerRepository.save(offer);

        sendMsg(offer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/ingredients/{id}").buildAndExpand(offer.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //EDIT
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editOffer(@PathVariable("id") long id, @Valid @RequestBody Offer offer) {

        Optional<Offer> currentOffer = offerRepository.findById(id);

        if (!currentOffer.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Offer with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentOffer.get().setName(offer.getName());
        currentOffer.get().setCategory(offer.getCategory());
        currentOffer.get().setAvg_review(offer.getAvg_review());
        currentOffer.get().setPrice(offer.getPrice());
        currentOffer.get().setPicture(offer.getPicture());
        currentOffer.get().setCakeShopId(offer.getCakeShopId());

        offerRepository.save(currentOffer.get());
        return new ResponseEntity<Optional<Offer>>(currentOffer, HttpStatus.OK);
    }

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOffer(@PathVariable("id") long id) {

        Optional<Offer> country =offerRepository.findById(id);
        if (!country.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Offer with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        try {
            deleteOfferAsync(country.get().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        offerRepository.deleteById(id);
        return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Offer> deleteAll() {

        offerRepository.deleteAll();
        return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
    }
}
