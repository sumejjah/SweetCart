package com.sweetcart.sweetcart.controller;

import com.sweetcart.sweetcart.entity.CakeShop;
import com.sweetcart.sweetcart.entity.Ingredient;
import com.sweetcart.sweetcart.entity.Offer;
import com.sweetcart.sweetcart.entity.Producer;
import com.sweetcart.sweetcart.entity.Request.AddOfferRequest;
import com.sweetcart.sweetcart.repository.OfferRepository;
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
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    Producer producer;

    @RequestMapping(method = RequestMethod.GET, value = "send/{offerId}")
    public String sendMsg(@PathVariable Long offerId){

        Optional<Offer> client = this.offerRepository.findById(offerId);
        if (client == null) {
            return "ne postoji";
        }

        producer.produceMsg(client.get());
        return "uspješno";
    }

    private OfferRepository offerRepository;

    @Autowired
    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /*//ALL
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Offer> findAllOffers ()
    {
        return offerRepository.findAll();
    }

    //ADD
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addOffer (@RequestBody AddOfferRequest addOfferRequest)
    {
        Offer offer = new Offer();
        offer.setName(addOfferRequest.getName());
        offer.setCategory(addOfferRequest.getCategory());
        //offer.setCake_shopid(addOfferRequest.getCake_shopid());
        offer.setAvg_review(addOfferRequest.getAvg_review());
        offer.setPrice(addOfferRequest.getPrice());
        offerRepository.save(offer);
    }*/
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
        currentOffer.get().setCakeShopId(offer.getCakeShopId());
        currentOffer.get().setIngredients(offer.getIngredients());

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
        offerRepository.deleteById(id);
        return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Offer> deleteAll() {

        offerRepository.deleteAll();
        return new ResponseEntity<Offer>(HttpStatus.NO_CONTENT);
    }
}
