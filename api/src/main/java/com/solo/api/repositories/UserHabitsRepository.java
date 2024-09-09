package com.solo.api.repositories;

import com.solo.api.models.UserHabits;

import jakarta.transaction.Transactional;

import java.sql.Time;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserHabitsRepository extends JpaRepository<UserHabits, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO appSolo.UserHabits(idUser, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke)" +
                    "VALUES(:idUser, :work, :workBegin, :workEnd, :study, :studyBegin, :studyEnd, :sleepBegin, :sleepEnd, :workout, :workoutBegin, :workoutEnd, :smoke)", nativeQuery = true)
    int registerUserHabits( @Param("idUser") Integer idUser,
                            @Param("work") boolean work,
                            @Param("workBegin") Time workBegin,
                            @Param("workEnd") Time workEnd,
                            @Param("study") boolean study,
                            @Param("studyBegin") Time studyBegin,
                            @Param("studyEnd") Time studyEnd,
                            @Param("sleepBegin") Time sleepBegin,
                            @Param("sleepEnd") Time sleepEnd,
                            @Param("workout") boolean workout,
                            @Param("workoutBegin") Time workoutBegin,
                            @Param("workoutEnd") Time workoutEnd,
                            @Param("smoke") boolean smoke);

    @Query(value = "SELECT * FROM appSolo.UserHabits WHERE idUser = :idUser", nativeQuery = true)
    UserHabits findHabitsById(@Param("idUser") Integer idUser);
/*
    @Transactional
    @Modifying
    @Query(value =  "UPDATE appSolo.UserHabits" +
                    " SET idUser = :idUser,"+
                    " work = :work,"+
                    " workBegin = :workBegin,"+
                    " workEnd = :workEnd,"+
                    " study = :study,"+
                    " studyBegin = :studyBegin,"+
                    " studyEnd = :studyEnd,"+
                    " sleepBegin = :sleepBegin,"+
                    " sleepEnd = :sleepEnd,"+
                    " workout = :workout,"+
                    " workoutBegin = :workoutBegin,"+
                    " workoutEnd = :workoutEnd,"+
                    " smoke = :smoke" + 
                    "WHERE idUser = :idUser", nativeQuery = true)
    int updateHabits(   @Param("idUser") Integer idUser,
                        @Param("work") boolean work,
                        @Param("workBegin") Time workBegin,
                        @Param("workEnd") Time workEnd,
                        @Param("study") boolean study,
                        @Param("studyBegin") Time studyBegin,
                        @Param("studyEnd") Time studyEnd,
                        @Param("sleepBegin") Time sleepBegin,
                        @Param("sleepEnd") Time sleepEnd,
                        @Param("workout") boolean workout,
                        @Param("workoutBegin") Time workoutBegin,
                        @Param("workoutEnd") Time workoutEnd,
                        @Param("smoke") boolean smoke);
*/

}
