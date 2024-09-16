package com.solo.api.controllers.workout;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.workout.MuscleActivity;
import com.solo.api.repositories.workout.MuscleActivityRepository;
import com.solo.api.services.workout.MuscleActivityService;

@RestController
@RequestMapping("/workout/muscle")
public class MuscleActivityController {
    @Autowired
    MuscleActivityRepository repository;

    @Autowired
    MuscleActivityService service;

    @PostMapping("/newActivity/{idUser}")
    public ResponseEntity<?> startNewMuscleActivity(@PathVariable Integer idUser){
        try{
            int idActivity = service.startNewMuscleActivity(idUser);

            if (idActivity != 1) {
                    return new ResponseEntity<>("Falha ao registrar atividade. Resultado da atualização: " + idActivity, HttpStatus.BAD_REQUEST);
                }
    
        // Retorna o ID do usuário recém-cadastrado
        return new ResponseEntity<>(Collections.singletonMap("idActivity", idActivity), HttpStatus.OK);        
        }
        catch(Exception e){
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/finishActivity/{idActivity}")
    public ResponseEntity<?> finishMuscleActivity(@PathVariable Integer idActivity, @RequestBody Map<String, String> body) {
        try {
            Time duration = (body.get("duration") != null && !body.get("duration").isEmpty()) ? Time.valueOf(body.get("duration") + ":00") : null;

            int result = service.finishMuscleActivity(duration, idActivity);
            
            if (result != 1) {
                return new ResponseEntity<>("Falha finalizar atividade. Resultado da atualização: " + result, HttpStatus.BAD_REQUEST);
            }
    
            return new ResponseEntity<>("Atividade finalizada com sucesso com sucesso", HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-activities/{idUser}")
    public List<MuscleActivity> findActivitiesByUser(@RequestParam Integer idUser) {
        return service.findActivitiesByUser(idUser);
    }

    @DeleteMapping("/my-activities/{idActivity}")
    public void delete(@PathVariable Integer idActivity){
        repository.deleteById(idActivity);
    }

}
