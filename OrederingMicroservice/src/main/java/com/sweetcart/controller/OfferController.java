package com.sweetcart.controller;

import com.sweetcart.entity.Offer;
import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddOfferRequest;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.OfferRepository;
import com.sweetcart.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/offer")
public class OfferController {

        private OfferRepository offerRepository;

        @Autowired
        public OfferController(OfferRepository offerRepository) {
            this.offerRepository = offerRepository;
        }

        @RequestMapping(value = "/all", method = RequestMethod.GET)
        public List<Offer> findAll(){
            return offerRepository.findAll();
        }

        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public void addOffer(@RequestBody AddOfferRequest addOfferRequest){
            Offer offer= new Offer();
            offer.setName(addOfferRequest.getName());
            offer.setAvg_review(addOfferRequest.getAvg_review());
            offer.setCategory(addOfferRequest.getCategory());
            offer.setPrice(addOfferRequest.getPrice());
            offer.setCake_shopid(addOfferRequest.getCake_shopid());

            offerRepository.save(offer);
        }

}
