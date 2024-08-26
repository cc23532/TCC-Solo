package com.solo.api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.solo.api.models.SoloUser;
import com.solo.api.repositories.SoloUserRepository;
import com.solo.api.services.SoloUserService;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    SoloUserService userService;

    @Autowired
    SoloUserRepository repository;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO: Login de Usuário no ar";
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String nickname = body.get("nickname");
        String pwd = body.get("pwd");
        
        System.out.println("Login chamado com nickname: " + nickname);
        System.out.println("Login chamado com senha: " + pwd);
        
        boolean isAuthenticated = userService.checkLogin(nickname, pwd);
        
        if (isAuthenticated) {
            // Retorne os dados do usuário se o login for bem-sucedido
            SoloUser user = repository.findUserByNickname(nickname);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais não correspondem ao usuário. Por favor, revise seus dados.");
        }
    }

}
