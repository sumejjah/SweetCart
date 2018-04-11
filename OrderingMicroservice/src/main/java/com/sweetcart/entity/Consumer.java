package com.sweetcart.entity;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Sumejja on 10.04.2018..
 */

@Component
public class Consumer {

    @RabbitListener(queues="${jsa.rabbitmq.queue}")
    public void recievedMessage(String msg) {
        System.out.println("Recieved Message: " + msg);
    }
}
