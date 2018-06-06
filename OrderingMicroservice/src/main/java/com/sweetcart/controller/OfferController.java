package com.sweetcart.controller;

import com.sweetcart.entity.Client;
import com.sweetcart.entity.Offer;
import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddOfferRequest;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.OfferRepository;
import com.sweetcart.repository.OrdersRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/offer")
@CrossOrigin(origins = "http://localhost:3000")
public class OfferController {

        private OfferRepository offerRepository;

        @Autowired
        public OfferController(OfferRepository offerRepository) {
            this.offerRepository = offerRepository;
        }

        // GET ALL OFFERS
        @RequestMapping(method = RequestMethod.GET, value = "/all")
        public ResponseEntity<Collection<Offer>> findAll() {
            Collection<Offer> clients = this.offerRepository.findAll();
            if(clients.isEmpty()){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Collection<Offer>>(clients, HttpStatus.OK);
        }

        // RETRIEVE ONE OFFER
        @RequestMapping(method = RequestMethod.GET, value = "/{clientId}")
        ResponseEntity<?> getOffer (@PathVariable Long clientId) {

            Optional<Offer> client = this.offerRepository.findById(clientId);
            if (!client.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Optional<Offer>>(client, HttpStatus.OK);
        }

        //CREATE NEW OFFER
        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer, UriComponentsBuilder ucBuilder) {

            Collection<Offer> offers = this.offerRepository.findAll();
            boolean exists = false;
            for (Iterator<Offer> i = offers.iterator(); i.hasNext();) {
                if(i.next().getName().equals(offer.getName()))
                    exists = true;
            }

            if (exists) {
                return new ResponseEntity(new CustomErrorType("Unable to create. A Country with name " + offer.getName() + " already exist."),HttpStatus.CONFLICT);
            }
            offerRepository.save(offer);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/offer/{id}").buildAndExpand(offer.getId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOneOffer(@Valid @RequestBody Offer offer) {

        Collection<Offer> offers = this.offerRepository.findAll();
        boolean exists = false;
        for (Iterator<Offer> i = offers.iterator(); i.hasNext();) {
            if(i.next().getName().equals(offer.getName()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A Country with name " + offer.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        offerRepository.save(offer);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE
        @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
        public ResponseEntity<?> updateOffer(@PathVariable("id") long id,@Valid @RequestBody Offer offer) {

            Optional<Offer> currentOffer = offerRepository.findById(id);

            if (!currentOffer.isPresent()) {
                return new ResponseEntity(new CustomErrorType("Unable to upate. Offer with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }

            currentOffer.get().setName(offer.getName());
            currentOffer.get().setAvg_review(offer.getAvg_review());
            currentOffer.get().setCake_shopid(offer.getCake_shopid());
            currentOffer.get().setCategory(offer.getCategory());
            currentOffer.get().setPrice(offer.getPrice());
        currentOffer.get().setPicture(offer.getPicture());
            currentOffer.get().setDescription(offer.getDescription());

        offerRepository.save(currentOffer.get());
            return new ResponseEntity<Optional<Offer>>(currentOffer, HttpStatus.OK);
        }

        //DELETE ONE
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<?> deleteOffer(@PathVariable("id") long id) {

            Optional<Offer> offer = offerRepository.findById(id);
            if (!offer.isPresent()) {
                return new ResponseEntity(new CustomErrorType("Unable to delete. Offer with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }
            offerRepository.deleteById(id);
            return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
        }

        //DELETE ALL
        @RequestMapping(method = RequestMethod.DELETE)
        public ResponseEntity<Offer> deleteAll() {

            offerRepository.deleteAll();
            return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
        }

        //DELETE BY NAME
        @RequestMapping(method = RequestMethod.DELETE, value = "/name/{userName}")
        ResponseEntity<?> deleteByName (@PathVariable String userName) {

            Collection<Offer> offers = this.offerRepository.findAll();
            Offer offer = new Offer();
            boolean exists = false;
            for (Offer u: offers) {
                if(u.getName().equals(userName)){
                    exists = true;
                    offer = u;
                }
            }

            if (!exists) {
                return new ResponseEntity(new CustomErrorType("Unable to delete. Offer with name " + userName + " not found."),
                        HttpStatus.NOT_FOUND);
            }

            offerRepository.deleteById(offer.getId());
            return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
        }





}
