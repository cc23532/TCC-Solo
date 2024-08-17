package com.solo.api.controllers;

import com.solo.api.models.SoloUser;
import com.solo.api.repositories.SoloUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class SoloUserController {
    @Autowired
    private SoloUserRepository repository;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO no ar";
    }

    @GetMapping
    public List<SoloUser> all(){
        return repository.findAll();
    }
 
    @GetMapping("/user/{id}")
    public Optional<SoloUser> one(@PathVariable Integer id){
        return repository.findById(id);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public SoloUser save(@RequestBody SoloUser newUser){
        return repository.save(newUser);
    }

    @PutMapping("/user/{id}")
    public SoloUser replace(@RequestBody SoloUser newUser, @PathVariable Integer id){
        return repository.findById(id)
                .map(user -> {
                    user.setNickname(newUser.getNickname());
                    user.setPwd(newUser.getPwd());
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
