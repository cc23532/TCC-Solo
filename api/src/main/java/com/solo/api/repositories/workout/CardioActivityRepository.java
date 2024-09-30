package com.solo.api.repositories.workout;

import com.solo.api.models.workout.CardioActivity;

import jakarta.transaction.Transactional;

import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardioActivityRepository extends JpaRepository<CardioActivity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.CardioActivity " + 
                    "SET duration = :duration, " +
                    "distance = :distance, " +
                    "averageSpeed = :averageSpeed, " +
                    "elevationGain = :elevationGain " +
                    "WHERE idActivity = :idActivity", nativeQuery = true)
    int finishCardioActivity(   @Param("duration") Time duration,
                                @Param("distance") double distance,
                                @Param("averageSpeed") double averageSpeed,
                                @Param("elevationGain") double elevationGain,
                                @Param("idActivity") Integer idActivity);

    
    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.CardioActivity " +
                "SET lostKCal = (" +
                "    CASE " +
                "        WHEN averageSpeed < 6 THEN 3.5 " +  // Caminhada leve
                "        WHEN averageSpeed < 9 THEN 7 " +    // Corrida leve
                "        ELSE 10 " +                         // Corrida intensa
                "    END " +
                ") * (SELECT weight FROM appSolo.SoloUser WHERE appSolo.SoloUser.id = appSolo.CardioActivity.idUser) " +
                "* (DATEPART(HOUR, duration) + DATEPART(MINUTE, duration) / 60.0 + DATEPART(SECOND, duration) / 3600.0) " +
                "* (1 + (elevationGain / 100.0)) " +          // Multiplicador baseado no ganho de elevação
                "WHERE idActivity = :idActivity", nativeQuery = true)
    int calculateLostKCal(@Param("idActivity") Integer idActivity);

    @Query(value = "SELECT lostKCal FROM appSolo.CardioActivity WHERE idActivity = :idActivity", nativeQuery = true)
    Double getLostKCal(@Param("idActivity") Integer idActivity);


    @Query(value = "SELECT * FROM appSolo.CardioActivity WHERE idUser = :idUser", nativeQuery = true)
    List<CardioActivity> findActivitiesByUser(@Param("idUser") Integer idUser);


}
