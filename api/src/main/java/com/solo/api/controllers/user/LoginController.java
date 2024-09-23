package com.solo.api.controllers.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.user.SoloUserRepository;
import com.solo.api.services.user.SoloUserService;

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
    
        SoloUser user = repository.findUserByNickname(nickname);
    
        if (user != null && userService.checkLogin(nickname, pwd)) {
            // Retornar os dados do usuário, incluindo o ID, se o login for bem-sucedido
            System.out.println("Login bem-sucedido para o usuário com ID: " + user.getId());
            return ResponseEntity.ok(Map.of(
                "message", "Login bem-sucedido",
                "idUser", user.getId(), 
                "nickname", user.getNickname()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais não correspondem ao usuário. Por favor, revise seus dados.");
        }
    }
    

}
