package com.solo.api.services.workout;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.repositories.workout.MuscleActivity_ExercisesRepository;

@Service
public class MuscleActivity_ExercisesService {
    @Autowired
    MuscleActivity_ExercisesRepository repository;

    public int registerNewActivityExercise(Integer idActivity, Integer idExercise, double weight, int series, int repetition){
        return registerNewActivityExercise(idActivity, idExercise, weight, series, repetition);
    }

    public List<Object[]> getMuscleActivityExercisesByActivity(Integer idActivity){
        return getMuscleActivityExercisesByActivity(idActivity);
    }
}
