package com.solo.api.controllers;

import com.solo.api.models.User;
import com.solo.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    GetMapping
    public List<User> all(){
        return repository.findAll();
    }

    @GetMapping("/user/{id}")
    public Optional<User> one(@PathVariable Integer id){
        return repository.findById(id);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public User save(@RequestBody User newUser){
        return repository.save(newUser);
    }

    @PutMapping("/user/{id}")
    public User replace(@RequestBody User newUser, @PathVariable Integer id){
        return repository.findById(id)
                .map(user -> {
                    user.setNickname(newUser.getNickname());
                    user.setPassword(newUser.getPassword());
                    user.setBirthday(newUser.getBirthday());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setWeight(newUser.getWeight());
                    user.setHeight(newUser.getHeight());
                    user.setProfile_pic(newUser.getProfile_pic());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    return repository.save(newUser);
                });
    }
}
