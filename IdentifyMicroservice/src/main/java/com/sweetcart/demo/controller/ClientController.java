package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.Client;
import com.sweetcart.demo.entity.request.AddClientRequest;
import com.sweetcart.demo.repository.ClientRepository;
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

@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // GET ALL CLIENTS
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Client>> findAll() {
        Collection<Client> clients = this.clientRepository.findAll();
        if(clients.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Client>>(clients, HttpStatus.OK);
    }

    // RETRIEVE ONE CLIENT
    @RequestMapping(method = RequestMethod.GET, value = "/{clientId}")
    ResponseEntity<?> getClient (@PathVariable Long clientId) {

        Optional<Client> client = this.clientRepository.findById(clientId);
        if (!client.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Client>>(client, HttpStatus.OK);
    }
    //CREATE NEW CLIENT
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, UriComponentsBuilder ucBuilder) {

        Collection<Client> clients = this.clientRepository.findAll();
        boolean exists = false;
        for (Iterator<Client> i = clients.iterator(); i.hasNext();) {
            if(i.next().getUserId().equals(client.getUserId()))
                exists = true;
            int x = 5;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A client with user " + client.getUserId() + " already exist."),HttpStatus.CONFLICT);
        }
        clientRepository.save(client);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/clients/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE CLIENT
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable("id") long id,@Valid @RequestBody Client client) {

        Optional<Client> currentClient = clientRepository.findById(id);

        if (!currentClient.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Client with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentClient.get().setFirstName(client.getFirstName());
        currentClient.get().setLastName(client.getLastName());
        currentClient.get().setBonus(client.getBonus());
        currentClient.get().setUserId(client.getUserId());

        clientRepository.save(currentClient.get());
        return new ResponseEntity<Optional<Client>>(currentClient, HttpStatus.OK);
    }
    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {

        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Client with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        clientRepository.deleteById(id);
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Client> deleteAll() {

        clientRepository.deleteAll();
        return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
    }
 /*   @RequestMapping(value="/all",method= RequestMethod.GET)
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
    */
}
