package com.sweetcart.controller;

import com.sweetcart.entity.Orders;
import com.sweetcart.entity.Requirement;
import com.sweetcart.entity.request.AddOrderRequest;
import com.sweetcart.entity.request.AddRequirementRequest;
import com.sweetcart.repository.OrdersRepository;
import com.sweetcart.repository.RequirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/requirement")
public class RequirementController {
    private RequirementRepository requirementRepository;

    @Autowired
    public RequirementController(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Requirement> findAll(){
        return requirementRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRequirement(@RequestBody AddRequirementRequest addRequirementRequest){
        Requirement requirement = new Requirement();
        requirement.setConfirmed(addRequirementRequest.isConfirmed());

        requirementRepository.save(requirement);
    }
}
