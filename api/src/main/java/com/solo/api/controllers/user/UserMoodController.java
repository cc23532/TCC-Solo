package com.solo.api.controllers.user;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.SoloUser;
import com.solo.api.models.UserMood;
import com.solo.api.repositories.SoloUserRepository;
import com.solo.api.repositories.UserMoodRepository;
import com.solo.api.services.UserMoodService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user/mood")
public class UserMoodController {

    @Autowired
    UserMoodRepository repository;

    @Autowired
    UserMoodService serivce;

    @GetMapping("/{idUser}")
    public Optional<UserMood> getMoodById(@PathVariable Integer idUser) {
        return repository.findById(idUser);
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<?> registerMood(@PathVariable Integer idUser, @RequestBody Map<String, String> body) {
        try {
            String mood = body.get("mood");

            int result = serivce.registerMood(idUser, mood);

            if (result != 1) {
                return new ResponseEntity<>("Falha ao registrar Humor. Resultado da atualização: " + result, HttpStatus.BAD_REQUEST);
            }

        return new ResponseEntity<>("Humor registrado com sucesso", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}