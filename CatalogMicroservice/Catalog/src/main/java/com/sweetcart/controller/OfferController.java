package com.sweetcart.controller;

import com.sweetcart.entity.Offer;
import com.sweetcart.entity.request.AddOfferRequest;
import com.sweetcart.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private OfferRepository offerRepository;

    @Autowired
    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Offer> findAllOffers ()
    {
        return offerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addOffer (@RequestBody AddOfferRequest addOfferRequest)
    {
        Offer offer = new Offer();
        offer.setName(addOfferRequest.getName());
        offer.setCategory(addOfferRequest.getCategory());
        offer.setCake_shopid(addOfferRequest.getCake_shopid());
        offer.setAvg_review(addOfferRequest.getAvg_review());
        offer.setPrice(addOfferRequest.getPrice());
        offerRepository.save(offer);
    }
}
