package com.sweetcart.controller;

import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Sumejja on 23.03.2018..
 */
@RestController
@RequestMapping(value = "/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Orders> findAll(){
        return ordersRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addOrder(@RequestBody AddOrderRequest addOrderRequest){
        Orders orders = new Orders();
        orders.setAdress(addOrderRequest.getAdress());
        orders.setTelephone(addOrderRequest.getTelephone());
        orders.setClient(addOrderRequest.getClient());
        orders.setOffer(addOrderRequest.getOffer());

        ordersRepository.save(orders);
    }
}
