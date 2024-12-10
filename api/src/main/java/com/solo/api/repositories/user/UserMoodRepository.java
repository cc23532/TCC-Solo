package com.solo.api.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.user.UserMood;

import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;

public interface UserMoodRepository extends JpaRepository<UserMood, Integer>{

    @Query(value = "SELECT * FROM appSolo.UserMood WHERE idUser = :idUser", nativeQuery = true)
    public List<UserMood> findMoodByIdUser(@Param("idUser") Integer idUser);
                            
}
