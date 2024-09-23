package com.solo.api.controllers.user;

import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.user.SoloUserRepository;
import com.solo.api.services.user.SoloUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class SoloUserController {
    @Autowired
    private SoloUserRepository repository;

    @Autowired
    private SoloUserService service;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO no ar";
    }

    @GetMapping
    public List<SoloUser> all(){
        return repository.findAll();
    }
 
    @GetMapping("/{id}")
    public Map<String, Object> findUserDetailsByIdUser(@PathVariable Integer id){
        return service.findUserDetailsByIdUser(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public SoloUser save(@RequestBody SoloUser newUser){
        return repository.save(newUser);
    }

    @GetMapping("/update/{id}")
    public Object[] findUserForUpdate(@PathVariable Integer id){
        return service.findUserForUpdate(id);
    }

    @PutMapping("/update/{id}")
    public SoloUser replace(@RequestBody SoloUser newUser, @PathVariable Integer id){
        return repository.findById(id)
                .map(user -> {
                    user.setNickname(newUser.getNickname());
                    user.setEmail(newUser.getEmail());
                    user.setPhone(newUser.getPhone());
                    user.setBirthday(newUser.getBirthday());
                    user.setGender(newUser.getGender());
                    user.setWeight(newUser.getWeight());
                    user.setHeight(newUser.getHeight());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    return repository.save(newUser);
                });
    }
}
