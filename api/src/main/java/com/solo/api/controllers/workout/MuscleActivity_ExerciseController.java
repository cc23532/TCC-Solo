package com.solo.api.controllers.workout;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.repositories.workout.MuscleActivity_ExercisesRepository;
import com.solo.api.services.workout.MuscleActivity_ExercisesService;

@RestController
@RequestMapping("/workout/muscle")
public class MuscleActivity_ExerciseController {
    @Autowired 
    MuscleActivity_ExercisesService service;

    @Autowired
    MuscleActivity_ExercisesRepository repository;

    @PostMapping("/newActivity/{idUser}/exercises/activityItems")
    public ResponseEntity<?> registerNewActivityExercise(@RequestParam Integer idActivity, @RequestParam Integer idExercise, @RequestBody Map<String, String> body){
        try {
            double weight = Double.parseDouble(body.get("weight"));
            int series = Integer.parseInt(body.get("series"));
            int repetition = Integer.parseInt(body.get("repetition"));

            int idMuscleActivity_Exercise = service.registerNewActivityExercise(idActivity, idExercise, weight, series, repetition);

            if(idMuscleActivity_Exercise <= 0){
                return new ResponseEntity<>("Falha ao cadastrar novo item de treino...", HttpStatus.BAD_REQUEST);

            }

            return new ResponseEntity<>(Collections.singletonMap("idMuscleActivity_Exercise", idMuscleActivity_Exercise), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("//newActivity/{idUser}/exercises/activityItems/{idActivity}")
    public List<Object[]> getMuscleActivityExercisesByActivity(@PathVariable Integer idActivity){
        return service.getMuscleActivityExercisesByActivity(idActivity);
    }
    
}