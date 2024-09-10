package com.solo.api.services.user;

import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.user.SoloUserRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SoloUserService {
    @Autowired
    SoloUserRepository repository;
    
    public int registerNewUser(String nickname, Date birthday, String email, String pwd, String phone, double weight, double height, String gender) {
        SoloUser newUser = new SoloUser();
        newUser.setNickname(nickname);
        newUser.setBirthday(birthday);
        newUser.setEmail(email);
        newUser.setPwd(pwd);
        newUser.setPhone(phone);
        newUser.setWeight(weight);
        newUser.setHeight(height);
        newUser.setGender(gender);
        
        SoloUser savedUser = repository.save(newUser);
        return savedUser.getId(); // Obtém o ID gerado
    }

    public boolean checkLogin(String nickname, String pwd){
        SoloUser user = repository.findUserByNickname(nickname);

        if(user != null){
            return BCrypt.checkpw(pwd, user.getPwd());
        }
        return false;
    }

    public Integer findUserIdForRecoverPwd(String nickname, String email, String phone){
        return repository.findUserIdForRecoverPwd(nickname, email, phone);
    }

    public int updatePwd(Integer id, String newPwd){
        String hash_pwd = BCrypt.hashpw(newPwd, BCrypt.gensalt());
        return repository.updatePwd(id, hash_pwd);
    }

}
