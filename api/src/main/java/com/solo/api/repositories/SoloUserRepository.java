package com.solo.api.repositories;

import com.solo.api.models.SoloUser;

import jakarta.transaction.Transactional;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoloUserRepository extends JpaRepository<SoloUser, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO appSolo.SoloUser(nickname, birthday, email, pwd, phone, weight, height) VALUES(:nickname, :birthday, :email, :pwd, :phone, :weight, :height)", nativeQuery = true)
    int registerNewUser(@Param("nickname") String nickname,
                        @Param("birthday") Date birthday,
                        @Param("email") String email,
                        @Param("pwd") String pwd,
                        @Param("phone") String phone,
                        @Param("weight") double weight,
                        @Param("height") double height);

    @Query(value = "SELECT * FROM appSolo.SoloUser WHERE nickname = :nickname", nativeQuery = true)
    SoloUser findUserByNickname(@Param("nickname") String nickname);
            
}

