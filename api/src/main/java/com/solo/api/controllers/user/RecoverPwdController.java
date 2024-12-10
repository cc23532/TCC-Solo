package com.solo.api.controllers.user;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.services.user.SoloUserService;

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
    public ResponseEntity<?> getUserRecoverPwd(@RequestBody Map<String, String> body) {
        String nickname = body.get("nickname");
        String email = body.get("email");
        String phone = body.get("phone");

        System.out.println("Nickname: " + nickname);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);

        Integer userId = userService.findUserIdForRecoverPwd(nickname, email, phone);

        if (userId != null) {
            // Retorna o userId como resposta
            return ResponseEntity.ok(userId);
        } else {
            // Retorna uma mensagem de erro com código 401 (não autorizado)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado... Tente novamente.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String newPwd = body.get("newPwd");

        if (newPwd == null || newPwd.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A nova senha é obrigatória.");
        }

        // Atualiza a senha
        int updated = userService.updatePwd(id, newPwd);

        if (updated > 0) {
            return ResponseEntity.ok("Senha atualizada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Falha ao atualizar a senha.");
        }
    }


}
