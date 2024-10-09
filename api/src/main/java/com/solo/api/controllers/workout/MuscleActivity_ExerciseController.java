package com.solo.api.controllers.workout;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.solo.api.DTO.MuscleActivityExerciseDTO;
import com.solo.api.models.workout.MuscleAc_Ex_ItemKey;
import com.solo.api.models.workout.MuscleActivity;
import com.solo.api.models.workout.MuscleExercise;
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
    public ResponseEntity<?> registerNewActivityExercise(
            @RequestParam Integer idActivity, 
            @RequestParam Integer idExercise, 
            @RequestBody Map<String, String> body) {
        try {
            String name = body.get("name");
            double weight = Double.parseDouble(body.get("weight"));
            int series = Integer.parseInt(body.get("series"));
            int repetition = Integer.parseInt(body.get("repetition"));

            // Busque a atividade e o exercício pelos IDs
            MuscleActivity activity = service.findActivityById(idActivity);
            MuscleExercise exercise = service.findExerciseById(idExercise);

            if (activity == null || exercise == null) {
                return new ResponseEntity<>("Atividade ou exercício não encontrados", HttpStatus.BAD_REQUEST);
            }

            MuscleAc_Ex_ItemKey itemKey = service.registerNewActivityExercise(activity, exercise, name, weight, series, repetition);

            if (itemKey == null) {
                return new ResponseEntity<>("Falha ao cadastrar novo item de treino...", HttpStatus.BAD_REQUEST);
            }

            // Retornar a resposta com os IDs
            Map<String, Object> response = new HashMap<>();
            response.put("idActivity", itemKey.getIdActivity());
            response.put("idExercise", itemKey.getIdExercise());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/newActivity/{idUser}/exercises/activityItems/{idActivity}")
    public ResponseEntity<List<MuscleActivityExerciseDTO>> getMuscleActivityExercisesByActivity(@PathVariable Integer idUser, @PathVariable Integer idActivity) {
        List<MuscleActivityExerciseDTO> exercises = service.getMuscleActivityExercisesByActivity(idUser, idActivity);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    
}
