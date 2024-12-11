package com.solo.api.controllers.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solo.api.models.user.SoloUser;
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

    @GetMapping("/count/{idUser}")
    public ResponseEntity<?> getMoodCountByTimeFrame(@PathVariable Integer idUser, @RequestBody Map<String, String> body) {
        String timeFrame = body.get("timeFrame");
        List<Object[]> results = repository.getMoodCountByTimeFrame(timeFrame, idUser);
        Map<String, Object> response = new HashMap<>();
    
        // Inicializar os counts com zero
        response.put("idUser", idUser);
        response.put("veryhappy", 0);
        response.put("happy", 0);
        response.put("normal", 0);
        response.put("unhappy", 0);
        response.put("sad", 0);
    
        // Preencher os moods existentes no resultado
        for (Object[] result : results) {
            String mood = (String) result[0]; // Nome do humor
            Integer count = (Integer) result[1];   // Quantidade de ocorrências
            if (response.containsKey(mood)) {
                response.put(mood, count != null ? count : 0);
            }
        }
    
        return ResponseEntity.status(HttpStatus.OK).body(response);
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