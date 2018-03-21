package com.sweetcart.controller;

import com.sweetcart.entity.Order;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sumejja on 20.03.2018..
 */
@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addOrder(@RequestBody AddOrderRequest addOrderRequest){
        Order order = new Order();
        order.setTelephone(addOrderRequest.getTelephone());
        order.setAdress(addOrderRequest.getAdress());
        order.setOfferid(addOrderRequest.getOfferid());
        order.setClientid(addOrderRequest.getClientid());

        orderRepository.save(order);
    }
}
