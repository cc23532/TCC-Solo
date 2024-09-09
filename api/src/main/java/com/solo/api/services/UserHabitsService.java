package com.solo.api.services;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.UserHabits;
import com.solo.api.repositories.UserHabitsRepository;

@Service
public class UserHabitsService {
    @Autowired
    UserHabitsRepository repository;

    public int registerUserHabits(Integer idUser, boolean work, Time workBegin, Time workEnd, boolean study, Time studyBegin, Time studyEnd, Time sleepBegin, Time sleepEnd, boolean workout, Time workoutBegin, Time workoutEnd, boolean smoke){
        return repository.registerUserHabits(idUser, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke);
    }

    public int updateHabits(Integer idUser, boolean work, Time workBegin, Time workEnd, boolean study, Time studyBegin, Time studyEnd, Time sleepBegin, Time sleepEnd, boolean workout, Time workoutBegin, Time workoutEnd, boolean smoke){
        return repository.updateHabits(idUser, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke);
    }

    public UserHabits findHabitsById(Integer idUser){
        return repository.findHabitsById(idUser);
    }
}
