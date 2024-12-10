package com.solo.api.controllers.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.solo.api.models.user.SoloUser;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.user.UserMood;
import com.solo.api.repositories.user.UserMoodRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/user/mood")
public class UserMoodController {

    @Autowired
    UserMoodRepository repository;

    @GetMapping("/{idUser}")
    public List<UserMood> getMoodById(@PathVariable Integer idUser) {
        return repository.findMoodByIdUser(idUser);
    }

    @PostMapping("/{idUser}")
    public ResponseEntity<?> registerMood(@PathVariable SoloUser idUser, @RequestBody Map<String, String> body) {
        try {
            String mood = body.get("mood");
            String moodDateStr = body.get("moodDate");

            Date moodDate;
            try {
                moodDate = new SimpleDateFormat("yyyy-MM-dd").parse(moodDateStr);

            } catch (Exception e) {
                return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
            }
            UserMood newMood = new UserMood();
            newMood.setMood(mood);
            newMood.setMoodDate(moodDate);
            newMood.setUser(idUser);
            repository.save(newMood);
            return ResponseEntity.status(HttpStatus.CREATED).body(newMood);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}