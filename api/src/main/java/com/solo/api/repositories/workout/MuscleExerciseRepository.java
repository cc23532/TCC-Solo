package com.solo.api.repositories.workout;

import com.solo.api.models.workout.MuscleExercise;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuscleExerciseRepository extends JpaRepository<MuscleExercise,Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO appSolo.MuscleExercise(idUser, name) VALUES (:idUser, :name)", nativeQuery = true)
    int registerNewExercise(@Param("idUser") Integer idUser,
                            @Param("name") String name);

    @Query(value = "SELECT * FROM appSolo.MuscleExercise WHERE idUser = :idUser", nativeQuery = true)
    List<MuscleExercise> findExercisesByUser(@Param("idUser") Integer idUser);
}
