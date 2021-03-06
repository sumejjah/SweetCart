package com.sweetcart.controller;

import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.OrdersRepository;
import org.aspectj.weaver.ast.Or;
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

/**
 * Created by Sumejja on 23.03.2018..
 */
@RestController
@RequestMapping(value = "/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrdersController {

    private OrdersRepository ordersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    /*@RequestMapping(value = "/all", method = RequestMethod.GET)
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
    }*/

    // GET ALL ORDERS
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Orders>> findAll() {
        Collection<Orders> orders = this.ordersRepository.findAll();
        if(orders.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Orders>>(orders, HttpStatus.OK);
    }

    // RETRIEVE ONE ORDER
    @RequestMapping(method = RequestMethod.GET, value = "/{orderId}")
    ResponseEntity<?> getOrder (@PathVariable Long orderId) {

        Optional<Orders> newOrder = this.ordersRepository.findById(orderId);
        if (!newOrder.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Orders>>(newOrder, HttpStatus.OK);
    }

    //CREATE NEW ORDER
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@Valid @RequestBody Orders orders, UriComponentsBuilder ucBuilder) {

        ordersRepository.save(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/order/{id}").buildAndExpand(orders.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateOrder(@PathVariable("id") long id,@Valid @RequestBody Orders orders) {

        Optional<Orders> currentOrder = ordersRepository.findById(id);

        if (!currentOrder.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Order with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentOrder.get().setTelephone(orders.getTelephone());
        currentOrder.get().setAdress(orders.getAdress());
        currentOrder.get().setClient(orders.getClient());
        currentOrder.get().setOffer(orders.getOffer());

        ordersRepository.save(currentOrder.get());
        return new ResponseEntity<Optional<Orders>>(currentOrder, HttpStatus.OK);
    }

    // RETRIEVE ONE USER BY NAME
    @RequestMapping(method = RequestMethod.GET, value = "/adress/{orderAdress}")
    ResponseEntity<?> getOrderByAdress (@PathVariable String orderAdress) {

        Collection<Orders> orders = this.ordersRepository.findAll();
        Orders order = new Orders();
        boolean exists = false;
        for (Orders u: orders) {
            if(u.getAdress().equals(orderAdress)){
                exists = true;
                order = u;
            }
        }

        if (!exists) {
            return new ResponseEntity(new CustomErrorType("Unable to login. A user with usernname " + orderAdress + " doesn't exist."),HttpStatus.CONFLICT);
        }


        return new ResponseEntity<Orders>(order, HttpStatus.OK);
    }
    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOrder(@PathVariable("id") long id) {

        Optional<Orders> offer = ordersRepository.findById(id);
        if (!offer.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Order with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        ordersRepository.deleteById(id);
        return new ResponseEntity<Orders>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Orders> deleteAll() {

        ordersRepository.deleteAll();
        return new ResponseEntity<Orders>(HttpStatus.NO_CONTENT);
    }
}
