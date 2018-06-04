package com.sweetcart.controller;

import com.sweetcart.entity.Orders;
import com.sweetcart.entity.Requirement;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.entity.request.AddRequirementRequest;
import com.sweetcart.repository.OrdersRepository;
import com.sweetcart.repository.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/requirement")
@CrossOrigin(origins = "http://localhost:3000")
public class RequirementController {
    private RequirementRepository requirementRepository;

    @Autowired
    public RequirementController(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    /*@RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Requirement> findAll(){
        return requirementRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRequirement(@RequestBody AddRequirementRequest addRequirementRequest){
        Requirement requirement = new Requirement();
        requirement.setConfirmed(addRequirementRequest.isConfirmed());

        requirementRepository.save(requirement);
    }*/

    // GET ALL OFFERS
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Requirement>> findAll() {
        Collection<Requirement> requirements = this.requirementRepository.findAll();
        if(requirements.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Requirement>>(requirements, HttpStatus.OK);
    }

    // RETRIEVE ONE OFFER
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET, value = "/{requirementId}")
    ResponseEntity<?> getRequirement (@PathVariable Long requirementId) {

        Optional<Requirement> requirement = this.requirementRepository.findById(requirementId);
        if (!requirement.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Requirement>>(requirement, HttpStatus.OK);
    }

    //CREATE NEW OFFER
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createRequirement(@Valid @RequestBody Requirement requirement, UriComponentsBuilder ucBuilder) {

        requirementRepository.save(requirement);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/requirement/{id}").buildAndExpand(requirement.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRequirement(@PathVariable("id") long id,@Valid @RequestBody Requirement requirement) {

        Optional<Requirement> currentRequirement = requirementRepository.findById(id);

        if (!currentRequirement.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Requirement with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentRequirement.get().setConfirmed(requirement.isConfirmed());
        currentRequirement.get().setOrdersId(requirement.getOrdersId());

        requirementRepository.save(currentRequirement.get());
        return new ResponseEntity<Optional<Requirement>>(currentRequirement, HttpStatus.OK);
    }

    //DELETE ONE
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRequirement(@PathVariable("id") long id) {

        Optional<Requirement> requirement = requirementRepository.findById(id);
        if (!requirement.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Requirement with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        requirementRepository.deleteById(id);
        return new ResponseEntity<Requirement>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Requirement> deleteAll() {

        requirementRepository.deleteAll();
        return new ResponseEntity<Requirement>(HttpStatus.NO_CONTENT);
    }
}
