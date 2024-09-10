package com.solo.api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.user.UserMood;

import jakarta.transaction.Transactional;

public interface UserMoodRepository extends JpaRepository<UserMood, Integer>{

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO appSolo.UserMood(idUser, mood)"+
                    "VALUES (:idUser, :mood)", nativeQuery = true)
    public int registerMood(@Param("idUser") Integer idUser,
                            @Param("mood") String mood);
                            
}
