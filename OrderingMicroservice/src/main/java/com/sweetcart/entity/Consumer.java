package com.sweetcart.entity;

import com.sweetcart.controller.CustomErrorType;
import com.sweetcart.repository.OfferRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.sweetcart.controller.OfferController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Sumejja on 10.04.2018..
 */

@Component
public class Consumer {

    private OfferRepository offerRepository;

    @Autowired
    private OfferController offerController = new OfferController(offerRepository);

    @RabbitListener(queues="${jsa.rabbitmq.queue}")
    public void recievedMessage(Offer offer) {

        System.out.println("Dobiveni id: " + offer.getCake_shopid());

        Long broj = Long.valueOf(1);
        offer.setCake_shopid(broj);


        //offerRepository.save(offer);
        //System.out.println(offer.getCake_shopid());
        //System.out.println(offer.getCategory());
        //System.out.println(offer.getName());
        //System.out.println(offer.getPrice());

        offerController.createOneOffer(offer);

        System.out.println("Recieved Message: " + offer.toString());

    }
}
