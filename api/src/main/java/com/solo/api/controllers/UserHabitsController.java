package com.solo.api.controllers;

import java.sql.Time;

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

import com.solo.api.models.UserHabits;
import com.solo.api.services.UserHabitsService;

@RestController
@RequestMapping("/habits")
public class UserHabitsController {

    @Autowired
    UserHabitsService service;

    @GetMapping("/{idUser}")
    public UserHabits getHabitsById(@PathVariable Integer idUser){
        return service.findHabitsById(idUser);        
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<?> updateHabits(  @PathVariable Integer idUser, 
                                            @RequestBody boolean work, 
                                            @RequestBody Time workBegin, 
                                            @RequestBody Time workEnd, 
                                            @RequestBody boolean study, 
                                            @RequestBody Time studyBegin, 
                                            @RequestBody Time studyEnd, 
                                            @RequestBody Time sleepBegin, 
                                            @RequestBody Time sleepEnd, 
                                            @RequestBody boolean workout, 
                                            @RequestBody Time workoutBegin, 
                                            @RequestBody Time workoutEnd, 
                                            @RequestBody boolean smoke){
        int updated = service.updateHabits(idUser, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke);

        if(updated > 0){
            return ResponseEntity.ok("Hábitos atualizados com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar Hábitos.");
        }
    }    
}