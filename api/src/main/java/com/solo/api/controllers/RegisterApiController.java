package com.solo.api.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<String> registerNewUser(
            @RequestParam("nickname") String nickname,
            @RequestParam("birthday") Date birthday,
            @RequestParam("email") String email,
            @RequestParam("pwd") String pwd,
            @RequestParam("phone") String phone,
            @RequestParam("weight") double weight,
            @RequestParam("height") double height,
            @RequestParam("gender") String gender) {

        // Validação
        if (nickname.isEmpty() || birthday == null || email.isEmpty() || pwd.isEmpty() || phone.isEmpty() || weight <= 0.0 || height <= 0.0 || gender.isEmpty()) {
            return new ResponseEntity<>("Por favor preencha todos os campos solicitados", HttpStatus.BAD_REQUEST);
        }

        // Criptografia da senha
        String hashPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());

        // Chamada do serviço
        int result = userService.registerNewUser(nickname, birthday, email, hashPwd, phone, weight, height, gender);

        if (result != 1) {
            return new ResponseEntity<>("Falha ao cadastrar novo usuário...", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Cadastro realizado com sucesso!", HttpStatus.OK);
    }
}