package com.solo.api.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.services.SoloUserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/recoverPwd")
public class RecoverPwdController {
    @Autowired
    SoloUserService userService;
    
    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO: Recuperação de Senha no Ar";
    }

    @GetMapping("/user")
    public void getUserRecoverPwd(@RequestParam String nickname,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  HttpServletResponse response) throws IOException {
        System.out.println("Nickname: " + nickname);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);

        Integer userId = userService.findUserIdForRecoverPwd(nickname, email, phone);

        if (userId != null) {
            // Redireciona para a rota de atualização de senha com o ID recuperado
            response.sendRedirect("/recoverPwd/" + userId);
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Usuário não encontrado... Tente novamente.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestParam String newPwd){
        int updated = userService.updatePwd(id, newPwd);

    if(updated > 0){
        return ResponseEntity.ok("Senha atualizada com sucesso!");
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar a senha.");
    }
}

}
