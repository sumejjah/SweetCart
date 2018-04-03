package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.Role;

import com.sweetcart.demo.entity.request.AddRoleRequest;
import com.sweetcart.demo.repository.RoleRepository;
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
@RequestMapping("/roles")
public class RoleController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    // GET ALL ROLES
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<Role>> findAll() {
        Collection<Role> roles = this.roleRepository.findAll();
        if(roles.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<Role>>(roles, HttpStatus.OK);
    }

    // RETRIEVE ONE ROLE
    @RequestMapping(method = RequestMethod.GET, value = "/{roleId}")
    ResponseEntity<?> getRole (@PathVariable Long roleId) {

        Optional<Role> role = this.roleRepository.findById(roleId);
        if (!role.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Role>>(role, HttpStatus.OK);
    }
    //CREATE NEW ROLE
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createRole(@Valid @RequestBody Role role, UriComponentsBuilder ucBuilder) {

        Collection<Role> roles = this.roleRepository.findAll();
        boolean exists = false;
        for (Iterator<Role> i = roles.iterator(); i.hasNext();) {
            if(i.next().getType().equals(role.getType()))
                exists = true;
            int x = 5;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A role with type " + role.getType() + " already exist."),HttpStatus.CONFLICT);
        }
        roleRepository.save(role);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/roles/{id}").buildAndExpand(role.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE ROLE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRole(@PathVariable("id") long id,@Valid @RequestBody Role role) {

        Optional<Role> currentRole = roleRepository.findById(id);

        if (!currentRole.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. Role with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentRole.get().setType(role.getType());



        roleRepository.save(currentRole.get());
        return new ResponseEntity<Optional<Role>>(currentRole, HttpStatus.OK);
    }
    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRole(@PathVariable("id") long id) {

        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. Role with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        roleRepository.deleteById(id);
        return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Role> deleteAll() {

        roleRepository.deleteAll();
        return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
    }


   /*
    @RequestMapping(value="/all",method= RequestMethod.GET)
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addRole(@RequestBody AddRoleRequest addRoleRequest){
        Role role=new Role();
        role.setType(addRoleRequest.getType());

        roleRepository.save(role);

    }*/
}
