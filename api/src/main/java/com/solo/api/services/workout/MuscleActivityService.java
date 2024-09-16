package com.solo.api.services.workout;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.workout.MuscleActivity;
import com.solo.api.repositories.workout.MuscleActivityRepository;

@Service
public class MuscleActivityService {
    @Autowired
    MuscleActivityRepository repository;

    public int startNewMuscleActivity(Integer idUser){
        return repository.startNewMuscleActivity(idUser);
    }

    public int finishMuscleActivity(Time duration, Integer idUser){
        return repository.finishMuscleActivity(duration, idUser);
    }

    public List<MuscleActivity> findActivitiesByUser(Integer idUser){
        return repository.findActivitiesByUser(idUser);
    }
}
