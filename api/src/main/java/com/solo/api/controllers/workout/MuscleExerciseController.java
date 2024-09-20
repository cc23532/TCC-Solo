package com.solo.api.controllers.workout;

import java.util.Collections;
import java.util.Map;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.workout.MuscleExercise;
import com.solo.api.repositories.workout.MuscleExerciseRepository;
import com.solo.api.services.workout.MuscleExerciseService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/workout/muscle")
public class MuscleExerciseController {

    @Autowired
    MuscleExerciseService service;

    @Autowired
    MuscleExerciseRepository repository;

    @PostMapping("/newActivity/{idUser}/exercises")
    public ResponseEntity<?> registerNewExercise(@PathVariable Integer idUser, @RequestBody Map<String, String> body){
        try {
            String name = body.get("name");

            if(name == null || name.isEmpty()){
                return new ResponseEntity<>("Por favor preencha todos os campos solicitados", HttpStatus.BAD_REQUEST);
            }

            int idExercise = service.registerNewExercise(idUser, name);
            if (idExercise <= 0){
                return new ResponseEntity<>("Falha ao cadastrar novo exercício...", HttpStatus.BAD_REQUEST);
            }

            // Retorna o ID do exercício recém-cadastrado
            return new ResponseEntity<>(Collections.singletonMap("idExercise", idExercise), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/newActivity/{idUser}/exercises")
    public List<MuscleExercise> findExercisesByUser(@PathVariable Integer idUser){
        return service.findExercisesByUser(idUser);
    }
    
}
