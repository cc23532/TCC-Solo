package com.solo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.services.SoloUserService;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    SoloUserService userService;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO: Login de Usuário no ar";
    }

    @GetMapping()
    public ResponseEntity<?> login(@RequestParam String nickname, @RequestParam String pwd){
        System.out.println("Login chamado com nickname: " + nickname); // Adicione um log para depuração
        System.out.println("Login chamado com senha: " + pwd); // Adicione um log para depuração
        boolean isAuthenticated = userService.checkLogin(nickname, pwd);
        if(isAuthenticated){
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais não correspondem ao usuário. Por favor, revise seus dados.");
        }
    }
}
