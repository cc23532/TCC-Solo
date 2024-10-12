package com.solo.api.services.workout;

import java.util.List;
import java.util.ArrayList;

import com.solo.api.DTO.workout.MuscleActivityExerciseDTO;
import com.solo.api.models.workout.MuscleAc_Ex_ItemKey;
import com.solo.api.models.workout.MuscleActivity;
import com.solo.api.models.workout.MuscleActivity_Exercises;
import com.solo.api.models.workout.MuscleExercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.repositories.workout.MuscleActivityRepository;
import com.solo.api.repositories.workout.MuscleActivity_ExercisesRepository;
import com.solo.api.repositories.workout.MuscleExerciseRepository;

@Service
public class MuscleActivity_ExercisesService {
    @Autowired
    MuscleActivity_ExercisesRepository repository;

    @Autowired
    MuscleActivityRepository activityRepository;

    @Autowired
    MuscleExerciseRepository exerciseRepository;

    public MuscleAc_Ex_ItemKey registerNewActivityExercise(MuscleActivity activity, MuscleExercise exercise, String name, double weight, int series, int repetition) {
        // Criar a chave composta manualmente
        MuscleAc_Ex_ItemKey itemKey = new MuscleAc_Ex_ItemKey(activity.getIdActivity(), exercise.getIdExercise());

        // Criar a entidade MuscleActivity_Exercises e definir a chave composta
        MuscleActivity_Exercises newExercise = new MuscleActivity_Exercises();
        newExercise.setIdMuscleActivity_Exercise(itemKey); // Definir a chave composta
        newExercise.setActivity(activity);
        newExercise.setExercise(exercise);
        newExercise.setName(name);
        newExercise.setWeight(weight);
        newExercise.setSeries(series);
        newExercise.setRepetition(repetition);

        // Salvar a entidade no banco de dados
        MuscleActivity_Exercises savedExercise = repository.save(newExercise);

        // Retornar a chave composta
        return savedExercise.getIdMuscleActivity_Exercise();
    }

    // Método para buscar a atividade pelo ID
    public MuscleActivity findActivityById(Integer idActivity) {
        return activityRepository.findById(idActivity).orElse(null);
    }

    // Método para buscar o exercício pelo ID
    public MuscleExercise findExerciseById(Integer idExercise) {
        return exerciseRepository.findById(idExercise).orElse(null);
    }

    public List<MuscleActivityExerciseDTO> getMuscleActivityExercisesByActivity(Integer idUser, Integer idActivity) {
        List<Object[]> results = repository.getMuscleActivityExercisesByActivity(idUser, idActivity);

        List<MuscleActivityExerciseDTO> dtoList = new ArrayList<>();
        for (Object[] result : results) {
            MuscleActivityExerciseDTO dto = new MuscleActivityExerciseDTO(
                (Integer) result[0],  // id_item
                (Integer) result[1],  // idActivity
                (Integer) result[2],  // idExercise
                (String) result[3],   // category
                (String) result[4],   // name
                (Double) result[5],   // weight
                (Integer) result[6],  // series
                (Integer) result[7]   // repetition
            );
            dtoList.add(dto);
        }

        return dtoList;
    }
}
