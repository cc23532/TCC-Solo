package com.solo.api.services.workout;

import java.time.LocalDateTime;
import java.util.List;

import com.solo.api.models.user.SoloUser;
import com.solo.api.models.workout.MuscleActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.workout.MuscleExercise;
import com.solo.api.repositories.workout.MuscleExerciseRepository;

@Service
public class MuscleExerciseService {
    @Autowired
    MuscleExerciseRepository repository;

    public int registerNewExercise(SoloUser user, String name){
            MuscleExercise newExercise = new MuscleExercise();
        newExercise.setUser(user);
        newExercise.setName(name);

        MuscleExercise savedExercise = repository.save(newExercise);
        return savedExercise.getIdExercise();
    }

 public List<MuscleExercise> findExercisesByUser(Integer idUser){
    return repository.findExercisesByUser(idUser);
 }
    
}
