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
import java.util.Optional;
import java.util.HashMap;

import com.solo.api.DTO.diet.Meal_ItemsDTO;
import com.solo.api.models.diet.Food_Select;
import com.solo.api.models.diet.Meal_Items_Key;
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

    @PostMapping("/addMeal/{idUser}/items/{idMeal}")
    public ResponseEntity<?> registerMealItems(
            @PathVariable SoloUser idUser, 
            @PathVariable Integer idMeal, // Aqui, pegamos o idMeal como Integer
            @RequestBody Map<String, String> body) {
        try {
            // Validação dos parâmetros recebidos no corpo da requisição
            String foodName = body.get("foodName");
            String weightStr = body.get("weight");

            if (foodName == null || weightStr == null) {
                return new ResponseEntity<>("Campos obrigatórios ausentes.", HttpStatus.BAD_REQUEST);
            }

            // Verifica e converte o peso para double
            double weight;
            try {
                weight = Double.parseDouble(weightStr);
            } catch (NumberFormatException e) {
                return new ResponseEntity<>("Peso inválido.", HttpStatus.BAD_REQUEST);
            }

            // Busca o alimento pelo nome e método de preparo
            Optional<Food_Select> foodOptional = foodService.findByFoodName(foodName);
            
            if (foodOptional.isPresent()) {
                Food_Select food = foodOptional.get(); 
                Meal_Items_Key itemKey = service.registerMeal_Items(mealRepo.findById(idMeal).orElseThrow(() -> 
                    new RuntimeException("Refeição não encontrada.")), 
                    food.getId_item(), // Passamos o id do alimento
                    weight);
                
                // Criação da resposta
                Map<String, Object> response = new HashMap<>();
                response.put("idMeal", itemKey.getIdMeal());
                response.put("idFood", itemKey.getIdFood());

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Alimento não encontrado.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
    
    // retorna os dados individuais de cada item (ex. calorias, proteinas, carboidratos etc)
    @GetMapping("/my-meals/{idUser}/items/{idMeal}")
    public List<Meal_ItemsDTO> getMealDetails(@PathVariable SoloUser idUser, @PathVariable Integer idMeal) {
        return service.getMealDetails(idMeal);
    }
    

}
