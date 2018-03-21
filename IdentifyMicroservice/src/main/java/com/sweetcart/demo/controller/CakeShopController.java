package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.CakeShop;

import com.sweetcart.demo.entity.request.AddCakeShopRequest;
import com.sweetcart.demo.repository.CakeShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cakeshops")
public class CakeShopController {
    private CakeShopRepository cakeShopRepository;

    @Autowired
    public CakeShopController(CakeShopRepository cakeShopRepository) {
        this.cakeShopRepository = cakeShopRepository;
    }
    @RequestMapping(value="/all",method= RequestMethod.GET)
    public List<CakeShop> findAllCakeShops(){
        return cakeShopRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addClient(@RequestBody AddCakeShopRequest addCakeShopRequest){
        CakeShop cakeShop=new CakeShop();
        cakeShop.setName(addCakeShopRequest.getName());
        cakeShop.setAddress(addCakeShopRequest.getAddress());
        cakeShop.setDescription(addCakeShopRequest.getDescription());
        cakeShop.setUserId(addCakeShopRequest.getUserId());

       cakeShopRepository.save(cakeShop);

    }
}
