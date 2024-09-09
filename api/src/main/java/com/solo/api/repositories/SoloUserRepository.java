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
    @Query(value = "INSERT INTO appSolo.SoloUser(nickname, birthday, email, pwd, phone, weight, height, gender) VALUES(:nickname, :birthday, :email, :pwd, :phone, :weight, :height, :gender)", nativeQuery = true)
    int registerNewUser(@Param("nickname") String nickname,
                        @Param("birthday") Date birthday,
                        @Param("email") String email,
                        @Param("pwd") String pwd,
                        @Param("phone") String phone,
                        @Param("weight") double weight,
                        @Param("height") double height,
                        @Param("gender") String gender);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.SoloUser" +
                    " SET nickname = :nickname,"+
                    " birthday = :birthday,"+
                    " email = :email,"+
                    " phone = :phone,"+
                    " weight = :weight,"+
                    " height = :height,"+
                    " gender = :gender,"+
                    "WHERE idUser = :idUser", nativeQuery = true)
    int updateSoloUser(@Param("nickname") String nickname,
                        @Param("birthday") Date birthday,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("weight") double weight,
                        @Param("height") double height,
                        @Param("gender") String gender);

    @Query(value = "SELECT * FROM appSolo.SoloUser WHERE nickname = :nickname", nativeQuery = true)
    SoloUser findUserByNickname(@Param("nickname") String nickname);

    @Query(value = "SELECT id FROM appSolo.SoloUser WHERE nickname = :nickname AND email = :email AND phone = :phone", nativeQuery = true)
    Integer findUserIdForRecoverPwd(@Param("nickname") String nickname, 
                                    @Param("email") String email,
                                    @Param("phone") String phone);

    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.SoloUser SET pwd = :pwd WHERE id = :id", nativeQuery = true)
    int updatePwd(@Param("id") Integer id, @Param("pwd") String pwd);
            
}

