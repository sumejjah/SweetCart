package com.sweetcart.controller;

import com.sweetcart.entity.Client;
import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddClientRequest;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.ClientRepository;
import com.sweetcart.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/client")
public class ClientController {

        private ClientRepository clientRepository;

        @Autowired
        public ClientController(ClientRepository clientRepository) {
            this.clientRepository = clientRepository;
        }

        @RequestMapping(value = "/all", method = RequestMethod.GET)
        public List<Client> findAll(){
            return clientRepository.findAll();
        }

        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public void addClient(@RequestBody AddClientRequest addClientRequest){
            Client client = new Client();
            client.setBonus(addClientRequest.getBonus());
            client.setFirstName(addClientRequest.getFirstName());
            client.setLastName(addClientRequest.getLastName());
            client.setUserid(addClientRequest.getUserid());

            clientRepository.save(client);
        }
}
