package com.solo.api.services.workout;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.user.SoloUser;
import com.solo.api.models.workout.CardioActivity;
import com.solo.api.repositories.workout.CardioActivityRepository;

import jakarta.transaction.Transactional;

@Service
public class CardioActivityService {
    @Autowired
    CardioActivityRepository repository;

    public int startNewCardioActivity(SoloUser user){
        CardioActivity newActivity = new CardioActivity();
        newActivity.setUser(user);
        newActivity.setActivityDate(LocalDateTime.now());

        CardioActivity savedActivity = repository.save(newActivity);
        return savedActivity.getIdActivity();
    }

    @Transactional
    public double finishCardioActivity(Time duration, double distance, double averageSpeed, double elevationGain, Integer idActivity) {
        // Atualiza os dados da atividade
        int result = repository.finishCardioActivity(duration, distance, averageSpeed, elevationGain, idActivity);
        if (result != 1) {
            throw new RuntimeException("Falha ao finalizar a atividade.");
        }

        // Calcula o valor de lostKCal
        repository.calculateLostKCal(idActivity);

        // Busca o valor atualizado de lostKCal
        Double lostKCal = repository.getLostKCal(idActivity);

        if (lostKCal == null) {
            throw new RuntimeException("Erro ao calcular as calorias perdidas.");
        }

        return lostKCal;
    }

    public int setLostKCal(Integer idActivity){
        return repository.calculateLostKCal(idActivity);
    }

    public List<CardioActivity> findActivitiesByUser(Integer idUser){
        return repository.findActivitiesByUser(idUser);
    }
    
}
