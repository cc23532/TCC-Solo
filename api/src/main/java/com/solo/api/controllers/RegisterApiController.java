package com.solo.api.controllers;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import com.solo.api.services.SoloUserService;

@RestController
@RequestMapping("/register")
public class RegisterApiController {
    @Autowired
    SoloUserService userService;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO: Cadastro de Usuário no ar";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody Map<String, String> body) {
        String nickname = body.get("nickname");
        String birthdayStr = body.get("birthday");
        String email = body.get("email");
        String pwd = body.get("pwd");
        String phone = body.get("phone");
        String weightStr = body.get("weight");
        String heightStr = body.get("height");
        String gender = body.get("gender");

        Date birthday;
        double weight;
        double height;
        try {
            birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
            weight = Double.parseDouble(weightStr);
            height = Double.parseDouble(heightStr);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
        }

        if (nickname.isEmpty() || birthday == null || email.isEmpty() || pwd.isEmpty() || phone.isEmpty() || weight <= 0.0 || height <= 0.0 || gender.isEmpty()) {
            return new ResponseEntity<>("Por favor preencha todos os campos solicitados", HttpStatus.BAD_REQUEST);
        }

        String hashPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        // Chamada ao serviço
        int userId = userService.registerNewUser(nickname, birthday, email, hashPwd, phone, weight, height, gender);

        if (userId <= 0) {
            return new ResponseEntity<>("Falha ao cadastrar novo usuário...", HttpStatus.BAD_REQUEST);
        }

        // Retorna o ID do usuário recém-cadastrado
        return new ResponseEntity<>(Collections.singletonMap("userId", userId), HttpStatus.OK);
    }


    @PostMapping("/habits/{userId}")
    public ResponseEntity<?> registerHabits(
                                @PathVariable Integer userId,
                                @RequestBody Map<String, String> body) {

    }
}
