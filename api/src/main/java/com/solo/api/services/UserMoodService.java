package com.solo.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.repositories.UserMoodRepository;

@Service
public class UserMoodService {
    @Autowired
    UserMoodRepository repository;

    public int registerMood(Integer idUser, String mood){
        return repository.registerMood(idUser, mood);
    }
}
