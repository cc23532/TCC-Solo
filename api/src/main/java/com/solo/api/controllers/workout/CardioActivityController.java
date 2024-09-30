package com.solo.api.controllers.workout;

import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.user.SoloUser;
import com.solo.api.models.workout.CardioActivity;
import com.solo.api.repositories.workout.CardioActivityRepository;
import com.solo.api.services.workout.CardioActivityService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/workout/cardio")
public class CardioActivityController {

    @Autowired
    CardioActivityRepository repository;

    @Autowired
    CardioActivityService service;

    @PostMapping("/newActivity/{idUser}")
    public ResponseEntity<?> startNewCardioActivity(@PathVariable SoloUser idUser){
        try{
            int idActivity = service.startNewCardioActivity(idUser);

            if (idActivity <= 0) {
                    return new ResponseEntity<>("Falha ao registrar atividade. Resultado da atualização: " + idActivity, HttpStatus.BAD_REQUEST);
                }
    
        // Retorna o ID da atividade recém-criada
        return new ResponseEntity<>(Collections.singletonMap("idActivity", idActivity), HttpStatus.OK);        
        }
        catch(Exception e){
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/finishActivity/{idActivity}")
    public ResponseEntity<?> finishCardioActivity(@PathVariable Integer idActivity, @RequestBody Map<String, String> body) {
        try {
            Time duration = (body.get("duration") != null && !body.get("duration").isEmpty()) ? Time.valueOf(body.get("duration") + ":00") : null;
            double distance = Double.parseDouble(body.get("distance"));
            double averageSpeed = Double.parseDouble(body.get("averageSpeed"));
            double elevationGain = Double.parseDouble(body.get("elevationGain"));

            // Finaliza a atividade e recupera as calorias perdidas
            double lostKCal = service.finishCardioActivity(duration, distance, averageSpeed, elevationGain, idActivity);

            return new ResponseEntity<>("Atividade finalizada com sucesso! Você queimou " + lostKCal + " calorias, parabéns!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/my-activities/{idUser}")
    public List<CardioActivity> findActivitiesByUser(@PathVariable Integer idUser) {
        return service.findActivitiesByUser(idUser);
    }

    @DeleteMapping("/my-activities/{idActivity}")
    public void delete(@PathVariable Integer idActivity){
        repository.deleteById(idActivity);
    }

    
    
}
