package com.solo.api.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.repositories.user.UserMoodRepository;

@Service
public class UserMoodService {
    @Autowired
    UserMoodRepository repository;

    public int registerMood(Integer idUser, String mood){
        return repository.registerMood(idUser, mood);
    }
}
