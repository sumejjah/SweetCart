package com.sweetcart.entity;

import com.sweetcart.controller.CustomErrorType;
import com.sweetcart.repository.OfferRepository;
import org.json.JSONObject;
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
    public void recievedMessage(String offer) {

        String dobiveni = offer;
        JSONObject jsonObject = new JSONObject(dobiveni);
        Iterator<String> it = jsonObject.keys();
        Offer offerNew = new Offer();

        while(it.hasNext()){
            String key = it.next();
            if(key.equals("name"))
                offerNew.setName(jsonObject.getString("name"));
            else if(key.equals("category"))
                offerNew.setCategory(jsonObject.getString("category"));
            else if(key.equals("picture"))
                offerNew.setPicture(jsonObject.getString("picture"));
            else if(key.equals("cake_shopid"))
                offerNew.setCake_shopid(jsonObject.getLong("cake_shopid"));
            else if(key.equals("avg_review"))
                offerNew.setAvg_review(jsonObject.getDouble("avg_review"));
            else if(key.equals("price"))
                offerNew.setPrice(jsonObject.getDouble("price"));


            }
        //System.out.println("novi json" + offerNew.toString());

        offerController.createOneOffer(offerNew);
    }
}
