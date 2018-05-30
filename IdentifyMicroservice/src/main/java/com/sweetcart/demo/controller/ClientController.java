package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.CakeShop;
import com.sweetcart.demo.entity.Client;
import com.sweetcart.demo.entity.User;
import com.sweetcart.demo.entity.request.AddClientRequest;
import com.sweetcart.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
//@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {
    private ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    public void addClientOrderMS(Long clientId) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("OrderingMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/client/addClient/" + clientId;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println("neka greska");
            System.out.println(ex);
        }
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, UriComponentsBuilder ucBuilder) {

        Collection<Client> clients = this.clientRepository.findAll();
        boolean exists = false;
        for (Iterator<Client> i = clients.iterator(); i.hasNext();) {
            if(i.next().getUserId().equals(client.getUserId()))
                exists = true;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A client with user " + client.getUserId() + " already exist."),HttpStatus.CONFLICT);
        }
        clientRepository.save(client);

        try {
            addClientOrderMS(getClientByName(client.getFirstName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/clients/{id}").buildAndExpand(client.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    Long getClientByName (String firstName){

        Collection<Client> clients = this.clientRepository.findAll();
        Client client = new Client();
        boolean exists = false;
        for (Client u: clients) {
            if(u.getFirstName().equals(firstName)){
                exists = true;
                client = u;
            }
        }
        if (!exists)
            return null;

        return client.getId();
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

        try {
            deleteClientOrderMS(client.getFirstName());
            addClientOrderMS(getClientByName(currentClient.get().getFirstName()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Optional<Client>>(currentClient, HttpStatus.OK);
    }


    public void deleteClientOrderMS(String clientName) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("OrderingMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/client/name/" + clientName;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl, HttpMethod.DELETE, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    //RETRIEVE ONE CAKE SHOP METHOD
    Client getClientByID(Long cakeShopId) {

        Collection<Client> clients = this.clientRepository.findAll();
        Client client = new Client();
        boolean exists = false;
        for (Client u: clients) {
            if(u.getId().equals(cakeShopId)){
                exists = true;
                client = u;
            }
        }

        if (!exists) {
            return null;
        }

        return client;
    }

    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {

        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Client with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        try {
            System.out.println(getClientByID(id).getFirstName());
            deleteClientOrderMS(getClientByID(id).getFirstName());
        } catch (IOException e) {
            e.printStackTrace();
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
}
