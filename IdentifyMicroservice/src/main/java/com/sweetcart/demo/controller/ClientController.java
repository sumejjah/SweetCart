package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.Client;
import com.sweetcart.demo.entity.request.AddClientRequest;
import com.sweetcart.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @RequestMapping(value="/all",method= RequestMethod.GET)
    public List<Client> findAllClients(){
        return clientRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addClient(@RequestBody AddClientRequest addClientRequest){
        Client client=new Client();
        client.setBonus(addClientRequest.getBonus());
        client.setFirstName(addClientRequest.getFirstName());
        client.setLastName(addClientRequest.getLastName());

        client.setUserId(addClientRequest.getUserId());

        clientRepository.save(client);

    }
}
