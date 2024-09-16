package com.solo.api.services.workout;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.workout.CardioActivity;
import com.solo.api.repositories.workout.CardioActivityRepository;

@Service
public class CardioActivityService {
    @Autowired
    CardioActivityRepository repository;

    public int startNewCardioActivity(Integer idUser){
        return repository.startNewCardioActivity(idUser);
    }

    public int finishCardioActivity(Time duration, double distance, double averageSpeed, Integer idActivity){
        return repository.finishCardioActivity(duration, distance, averageSpeed, idActivity);
    }

    public int setLostKCal(Integer idActivity){
        return repository.calculateLostKCal(idActivity);
    }

    public List<CardioActivity> findActivitiesByUser(Integer idUser){
        return repository.findActivitiesByUser(idUser);
    }
    
}
