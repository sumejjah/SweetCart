package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.User;
import com.sweetcart.demo.entity.request.AddUserRequest;
import com.sweetcart.demo.repository.UserRepository;
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
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


   // @RequestMapping(value="/all",method= RequestMethod.GET)
   // public List<User> findAllUsers(){return userRepository.findAll();}

    // GET ALL USERS
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<Collection<User>> findAll() {
        Collection<User> users = this.userRepository.findAll();
        if(users.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    // RETRIEVE ONE USER
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    ResponseEntity<?> getUser (@PathVariable Long userId) {

        Optional<User> user = this.userRepository.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<User>>(user, HttpStatus.OK);
    }
    //CREATE NEW USER
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, UriComponentsBuilder ucBuilder) {

        Collection<User> users = this.userRepository.findAll();
        boolean exists = false;
        for (Iterator<User> i = users.iterator(); i.hasNext();) {
            if(i.next().getUsername().equals(user.getUsername()))
                exists = true;
            int x = 5;
        }

        if (exists) {
            return new ResponseEntity(new CustomErrorType("Unable to create. A user with usernname " + user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //UPDATE USER
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id,@Valid @RequestBody User user) {

        Optional<User> currentUser = userRepository.findById(id);

        if (!currentUser.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.get().setUsername(user.getUsername());
        currentUser.get().setPassword(user.getPassword());
        currentUser.get().setRoleId(user.getRoleId());


        userRepository.save(currentUser.get());
        return new ResponseEntity<Optional<User>>(currentUser, HttpStatus.OK);
    }
    //DELETE ONE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    //DELETE ALL
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAll() {

        userRepository.deleteAll();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /*@RequestMapping(value="/add", method=RequestMethod.POST)
    public void addUser(@RequestBody AddUserRequest addUserRequest){
        User user=new User();
        user.setUsername(addUserRequest.getUsername());
        user.setPassword(addUserRequest.getPassword());
        user.setRoleId(addUserRequest.getRoleId());
        userRepository.save(user);

    }
*/

}
