package com.solo.api.controllers.user;

import java.sql.Time;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.SoloUser;
import com.solo.api.models.UserHabits;
import com.solo.api.repositories.UserHabitsRepository;
import com.solo.api.services.UserHabitsService;

@RestController
@RequestMapping("/habits")
public class UserHabitsController {

    @Autowired
    UserHabitsRepository repository;

    @Autowired
    UserHabitsService service;

    @GetMapping("/{idUser}")
    public UserHabits getHabitsById(@PathVariable Integer idUser){
        return service.findHabitsById(idUser);        
    }

    /*
    @GetMapping("/{idUser}")
    public Optional<UserHabits> one(@PathVariable Integer id){
        return repository.findById(id);
    } */

    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateHabits(@PathVariable Integer idUser, @RequestBody UserHabits newHabits) {
        return repository.findById(idUser)
                .map(existingHabits -> {
                    existingHabits.setWork(newHabits.isWork());
                    existingHabits.setWorkBegin(newHabits.getWorkBegin());
                    existingHabits.setWorkEnd(newHabits.getWorkEnd());
                    existingHabits.setStudy(newHabits.isStudy());
                    existingHabits.setStudyBegin(newHabits.getStudyBegin());
                    existingHabits.setStudyEnd(newHabits.getStudyEnd());
                    existingHabits.setSleepBegin(newHabits.getSleepBegin());
                    existingHabits.setSleepEnd(newHabits.getSleepEnd());
                    existingHabits.setWorkout(newHabits.isWorkout());
                    existingHabits.setWorkoutBegin(newHabits.getWorkoutBegin());
                    existingHabits.setWorkoutEnd(newHabits.getWorkoutEnd());
                    existingHabits.setSmoke(newHabits.isSmoke());
                    repository.save(existingHabits);
                    return ResponseEntity.ok("Hábitos atualizados com sucesso!");
                })
                .orElseGet(() -> {
                    newHabits.setIdUser(idUser);
                    repository.save(newHabits);
                    return ResponseEntity.ok("Hábitos criados com sucesso!");
                });
    }
    
}