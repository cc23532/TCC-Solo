package com.solo.api.controllers.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.List;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collections;


import com.solo.api.repositories.diet.MealRepository;
import com.solo.api.services.diet.MealService;
import com.solo.api.DTO.diet.MealSummaryDTO;
import com.solo.api.models.user.SoloUser;


@RestController
@RequestMapping("/diet")
public class MealController {
    @Autowired
    MealRepository repo;

    @Autowired
    MealService service;

    @GetMapping
    public String testRoute() {
        return "SOLO: Seção de Dieta no ar";
    }
    

    //adiciona nova refeição
    @PostMapping("/addMeal/{idUser}")
    public ResponseEntity<?> registerNewMeal(@PathVariable SoloUser idUser, @RequestBody Map<String, String> body){
        try {
            String mealDateStr = body.get("mealDate");
            Date mealDate = new SimpleDateFormat("yyyy-MM-dd").parse(mealDateStr);
            Time mealTime = (body.get("mealTime") != null && !body.get("mealTime").isEmpty()) ? Time.valueOf(body.get("mealTime") + ":00") : null;

            int idMeal = service.registerNewMeal(idUser, mealDate, mealTime);
        
            if(idMeal <= 0){
                return new ResponseEntity<>("Falha ao registrar refeição. Resultado da atualização: " + idMeal, HttpStatus.BAD_REQUEST);
            }
        
            return new ResponseEntity<>(Collections.singletonMap("idMeal", idMeal), HttpStatus.OK);        

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // retorna todas as refeições e os dados gerais de cada uma com base no idUser
    // dados gerais = idMeal, data e hora, soma de calorias de todos itens etc
    @GetMapping("/my-meals/{idUser}")
    public List<MealSummaryDTO> getMealsByUser(@PathVariable Integer idUser) {
        return service.getMealsByUser(idUser);
    }    
}
