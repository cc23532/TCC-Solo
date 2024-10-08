package com.solo.api.repositories.workout;

import com.solo.api.models.workout.MuscleActivity;

import jakarta.transaction.Transactional;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MuscleActivityRepository extends JpaRepository<MuscleActivity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.MuscleActivity " +
                    "SET duration = :duration " +
                    "WHERE idUser = :idUser", nativeQuery = true)
    int finishMuscleActivity(@Param("duration") Time duration,
                             @Param("idUser") Integer idUser);
    
    @Query(value = "SELECT * FROM appSolo.MuscleActivity WHERE idUser = :idUser", nativeQuery = true)
    List<MuscleActivity> findActivitiesByUser(@Param("idUser") Integer idUser);


}
