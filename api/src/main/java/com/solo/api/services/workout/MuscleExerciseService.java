package com.solo.api.services.workout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.workout.MuscleExercise;
import com.solo.api.repositories.workout.MuscleExerciseRepository;

@Service
public class MuscleExerciseService {
    @Autowired
    MuscleExerciseRepository repository;

 public int registerNewExercise(Integer idUser, String name){
    return repository.registerNewExercise(idUser, name);
 }

 public List<MuscleExercise> findExercisesByUser(Integer idUser){
    return repository.findExercisesByUser(idUser);
 }
    
}
