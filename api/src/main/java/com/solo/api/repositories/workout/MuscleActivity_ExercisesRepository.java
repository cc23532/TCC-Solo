package com.solo.api.repositories.workout;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.workout.MuscleAc_Ex_ItemKey;
import com.solo.api.models.workout.MuscleActivity_Exercises;

import jakarta.transaction.Transactional;

public interface MuscleActivity_ExercisesRepository extends JpaRepository<MuscleActivity_Exercises, MuscleAc_Ex_ItemKey>{

    @Transactional
    @Modifying
    @Query(value =  "INSERT INTO appSolo.MuscleActivity_Exercises" + 
                    "(idActivity, idExercise, weight, series, repetition)" +
                    "VALUES (:idActivity, :idExercise, :weight, :series, :repetition)", nativeQuery = true)
    int registerNewActivityExercise(   @Param("idActivity") Integer idActivity,
                                        @Param("idExercise") Integer idExercise,
                                        @Param("weight") double weight,
                                        @Param("series") int series,
                                        @Param("series") int repetition);
    
    @Transactional
    @Modifying
    @Query(value =  "SELECT mae.idMuscleActivity_Exercise, " +
                    "mae.idActivity, " +
                    "mae.idExercise, " +
                    "me.name AS category, " +
                    "mae.name, " +
                    "mae.weight, " +
                    "mae.series, " +
                    "mae.repetition " +
                    "FROM appSolo.MuscleActivity_Exercises mae " +
                    "JOIN appSolo.MuscleExercise me ON mae.idExercise = me.idExercise " +
                    "WHERE me.idUser = :idUser AND mae.idActivity = :idActivity", nativeQuery = true)
    List<Object[]> getMuscleActivityExercisesByActivity(@Param("idUser") Integer idUser,
                                                        @Param("idActivity") Integer idActivity);

    
}
