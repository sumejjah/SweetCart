package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.Role;

import com.sweetcart.demo.entity.request.AddRoleRequest;
import com.sweetcart.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleRepository roleRepository;

   @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @RequestMapping(value="/all",method= RequestMethod.GET)
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addRole(@RequestBody AddRoleRequest addRoleRequest){
        Role role=new Role();
        role.setType(addRoleRequest.getType());

        roleRepository.save(role);

    }
}
