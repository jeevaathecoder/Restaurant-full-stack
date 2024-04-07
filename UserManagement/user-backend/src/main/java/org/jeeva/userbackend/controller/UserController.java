package org.jeeva.userbackend.controller;

import org.jeeva.userbackend.exception.UserNotFoundException;
import org.jeeva.userbackend.model.User;
import org.jeeva.userbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000" , allowedHeaders = "*" )
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/user")
    public User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/user")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserByID(@PathVariable("id") Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    public User updateUserById(@RequestBody User user,@PathVariable("id") Long id){
        return userRepository.findById(id)
                .map(u ->{
                    user.setUserName(user.getUserName());
                    user.setName(user.getName());
                    user.setEmail(user.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted with id: "+id);
    }
}
