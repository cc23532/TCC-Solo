package com.solo.api.services.workout;

import java.time.LocalDateTime;
import java.util.List;

import com.solo.api.models.user.SoloUser;
import com.solo.api.models.workout.MuscleActivity;
import com.solo.api.models.workout.MuscleActivity_Exercises;
import com.solo.api.models.workout.MuscleExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.repositories.workout.MuscleActivity_ExercisesRepository;

@Service
public class MuscleActivity_ExercisesService {
    @Autowired
    MuscleActivity_ExercisesRepository repository;

    public int registerNewActivityExercise(MuscleActivity activity, MuscleExercise exercise, double weight, int series, int repetition){
        MuscleActivity_Exercises newExercise = new MuscleActivity_Exercises();
        newExercise.setActivity(activity);
        newExercise.setExercise(exercise);
        newExercise.setWeight(weight);
        newExercise.setSeries(series);
        newExercise.setRepetition(repetition);

        MuscleActivity_Exercises savedExercise = repository.save(newExercise);
        return savedExercise.getIdItem();
    }

    public List<Object[]> getMuscleActivityExercisesByActivity(Integer idActivity){
        return getMuscleActivityExercisesByActivity(idActivity);
    }
}
