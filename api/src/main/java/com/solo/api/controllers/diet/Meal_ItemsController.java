package com.solo.api.controllers.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

import com.solo.api.DTO.diet.Meal_ItemsDTO;
import com.solo.api.models.diet.Meal;
import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.diet.MealRepository;
import com.solo.api.repositories.diet.Meal_ItemsRepository;
import com.solo.api.services.diet.Data_IBGEService;
import com.solo.api.services.diet.Meal_ItemsService;

@RestController
@RequestMapping("/diet")
public class Meal_ItemsController {
    @Autowired
    Meal_ItemsService service;
    
    @Autowired
    Data_IBGEService foodService;

    @Autowired
    Meal_ItemsRepository repo;

    @Autowired
    MealRepository mealRepo;

    //adiciona os alimentos da refeição
    @PostMapping("/addMeal/{idUser}?idMeal={idMeal}/items")
    public ResponseEntity<?> registerMeal_Items(@PathVariable SoloUser idUser, @RequestParam Integer idMeal, @RequestBody Map<String, String> body) {
        try {
            String foodName = body.get("foodName");
            String preparationMethod = body.get("preparationMethod");
    
            Optional<Integer> idFoodOptional = foodService.findByNameAndMethod(foodName, preparationMethod);
    
            if (idFoodOptional.isPresent()) {
                Integer idFood = idFoodOptional.get();
                return new ResponseEntity<>(Collections.singletonMap("idFood", idFood), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Alimento não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    // retorna os dados individuais de cada item (ex. calorias, proteinas, carboidratos etc)
    @GetMapping("/my-meals/{idUser}?idMeal={idMeal}/items")
    public List<Meal_ItemsDTO> getMealDetails(@PathVariable SoloUser idUser, @RequestParam Integer idMeal) {
        // Verifique se a refeição pertence ao usuário
        Meal meal = mealRepo.findById(idMeal)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Refeição não encontrada."));
        
        // Verifique se a refeição pertence ao usuário
        if (!meal.getUser().equals(idUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso não autorizado.");
        }
        
        return service.getMealDetails(idMeal);
    }
    

}
