package com.sweetcart.controller;

import com.sweetcart.entity.Client;
import com.sweetcart.entity.Orders;
import com.sweetcart.entity.request.AddClientRequest;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.repository.ClientRepository;
import com.sweetcart.repository.OrdersRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(value = "/client")
//@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {

        public ClientController(){}

        @Autowired
        private DiscoveryClient discoveryClient;


    @RequestMapping(method = RequestMethod.GET, value = "addClient/{clientId}")
    public void getUsers(@PathVariable Long clientId) throws RestClientException, IOException {

        List<ServiceInstance> instances=discoveryClient.getInstances("IdentifyMicroservice-service-client");
        ServiceInstance serviceInstance=instances.get(0);

        String baseUrl=serviceInstance.getUri().toString();

        baseUrl=baseUrl+"/clients/" + clientId.toString();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        String dobiveni = response.getBody();
        JSONObject jsonObject = new JSONObject(dobiveni);
        Iterator<String> it = jsonObject.keys();
        Client client = new Client();

        while(it.hasNext()){
            String key = it.next();
            if(key.equals("firstName"))
                client.setFirstName(jsonObject.getString("firstName"));
            else if(key.equals("lastName"))
                client.setLastName(jsonObject.getString("lastName"));
            else if(key.equals("id"))
                client.setId(jsonObject.getLong("id"));
            else if(key.equals("bonus"))
                client.setBonus(jsonObject.getInt("bonus"));
            else if (key.equals("userId")){
                JSONObject sub = (JSONObject) jsonObject.get(key);
                client.setUserid(sub.getLong("id"));
            }

        }

        createClientIdentify(client);
        //System.out.println(response.getBody());
    }

    private static HttpEntity<?> getHeaders() throws IOException {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            return new HttpEntity<>(headers);
        }

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
        @RequestMapping(value = "/add", method = RequestMethod.POST)
        public ResponseEntity<?> createClient(@Valid @RequestBody Client client, UriComponentsBuilder ucBuilder) {

            Collection<Client> clients = this.clientRepository.findAll();
            boolean exists = false;
            for (Iterator<Client> i = clients.iterator(); i.hasNext();) {
                if(i.next().getFirstName().equals(client.getFirstName()))
                    exists = true;
            }

            if (exists) {
                return new ResponseEntity(new CustomErrorType("Unable to create. A Country with name " + client.getFirstName() + " already exist."),HttpStatus.CONFLICT);
            }
            clientRepository.save(client);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/client/{id}").buildAndExpand(client.getId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }

        //CREATE NEW CLIENT - samo za komunikaciju između identify i order
        @RequestMapping(value = "/addNew", method = RequestMethod.POST)
        public boolean createClientIdentify(Client client) {

            Collection<Client> clients = this.clientRepository.findAll();
            boolean exists = false;
            for (Iterator<Client> i = clients.iterator(); i.hasNext();) {
                if(i.next().getFirstName().equals(client.getFirstName()))
                    exists = true;
            }

            if (exists) {
                return false;
            }
            clientRepository.save(client);

            return true;
        }


        //UPDATE
        @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
        public ResponseEntity<?> updateClient(@PathVariable("id") long id,@Valid @RequestBody Client client) {

            Optional<Client> currentClient = clientRepository.findById(id);

            if (!currentClient.isPresent()) {
                return new ResponseEntity(new CustomErrorType("Unable to upate. Client with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }

            currentClient.get().setFirstName(client.getFirstName());
            currentClient.get().setLastName(client.getLastName());
            currentClient.get().setBonus(client.getBonus());
            currentClient.get().setUserid(client.getUserid());

            clientRepository.save(currentClient.get());
            return new ResponseEntity<Optional<Client>>(currentClient, HttpStatus.OK);
        }

        // RETRIEVE ONE CLIENT BY NAME
        @RequestMapping(method = RequestMethod.DELETE, value = "/name/{userName}")
        ResponseEntity<?> deleteByName (@PathVariable String userName) {

            Collection<Client> clients = this.clientRepository.findAll();
            Client client = new Client();
            boolean exists = false;
            for (Client u: clients) {
                if(u.getFirstName().equals(userName)){
                    exists = true;
                    client = u;
                }
            }

            if (!exists) {
                return new ResponseEntity(new CustomErrorType("Unable to delete. CakeShop with name " + userName + " not found."),
                        HttpStatus.NOT_FOUND);
            }

            clientRepository.deleteById(client.getId());
            return new ResponseEntity<Client>(HttpStatus.NO_CONTENT);
        }


        //DELETE ONE
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<?> deleteClient(@PathVariable("id") long id) {

            Optional<Client> country = clientRepository.findById(id);
            if (!country.isPresent()) {
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

}
