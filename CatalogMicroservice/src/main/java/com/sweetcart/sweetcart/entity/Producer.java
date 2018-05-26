package com.sweetcart.sweetcart.entity;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Created by Sumejja on 10.04.2018..
 */

@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${jsa.rabbitmq.exchange}")
    private String exchange;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingKey;

    public void produceMsg(Offer offer){
        amqpTemplate.convertAndSend(exchange, routingKey, offer);
        System.out.println("Send msg = " + offer.toString());
    }
}
