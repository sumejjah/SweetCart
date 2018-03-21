package com.sweetcart.demo.controller;

import com.sweetcart.demo.entity.User;
import com.sweetcart.demo.entity.request.AddUserRequest;
import com.sweetcart.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @RequestMapping(value="/all",method= RequestMethod.GET)
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void addUser(@RequestBody AddUserRequest addUserRequest){
        User user=new User();
        user.setUsername(addUserRequest.getUsername());
        user.setPassword(addUserRequest.getPassword());
        user.setRoleId(addUserRequest.getRoleId());
        userRepository.save(user);

    }
}
