package com.solo.api.repositories.user;

import com.solo.api.models.user.SoloUser;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SoloUserRepository extends JpaRepository<SoloUser, Integer> {

    // Atualiza o usuário com base em seus dados e ID
    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.SoloUser" +
                   " SET nickname = :nickname," +
                   " birthday = :birthday," +
                   " email = :email," +
                   " phone = :phone," +
                   " weight = :weight," +
                   " height = :height," +
                   " gender = :gender" +
                   " WHERE id = :id", nativeQuery = true)
    int updateSoloUser(@Param("nickname") String nickname,
                       @Param("birthday") Date birthday,
                       @Param("email") String email,
                       @Param("phone") String phone,
                       @Param("weight") double weight,
                       @Param("height") double height,
                       @Param("gender") String gender,
                       @Param("id") Integer id);

    // Busca um usuário por apelido
    @Query(value = "SELECT * FROM appSolo.SoloUser WHERE nickname = :nickname", nativeQuery = true)
    SoloUser findUserByNickname(@Param("nickname") String nickname);

    @Query(value = "SELECT * FROM appSolo.UserDetails WHERE idUser = :idUser", nativeQuery = true)
    Map<String, Object> findUserDetailsByIdUser(@Param("idUser") Integer idUser);

    @Query(value = "SELECT nickname, email, phone, birthday, gender, height, weight FROM appSolo.SoloUser WHERE id = :id")
    Object[] findUserForUpdate(@Param("id") Integer id);


    // Encontra o ID de um usuário para recuperação de senha com base em nome, e-mail e telefone
    @Query(value = "SELECT id FROM appSolo.SoloUser WHERE nickname = :nickname AND email = :email AND phone = :phone", nativeQuery = true)
    Integer findUserIdForRecoverPwd(@Param("nickname") String nickname, 
                                    @Param("email") String email,
                                    @Param("phone") String phone);

    // Atualiza a senha de um usuário com base no ID
    @Transactional
    @Modifying
    @Query(value = "UPDATE appSolo.SoloUser SET pwd = :pwd WHERE id = :id", nativeQuery = true)
    int updatePwd(@Param("id") Integer id, @Param("pwd") String pwd);
}
