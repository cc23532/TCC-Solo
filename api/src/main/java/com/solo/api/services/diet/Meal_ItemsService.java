package com.solo.api.services.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.math.BigDecimal;

import com.solo.api.models.diet.Meal;
import com.solo.api.models.diet.Meal_Items;
import com.solo.api.models.diet.Meal_Items_Key;
import com.solo.api.DTO.diet.Meal_ItemsDTO;
import com.solo.api.repositories.diet.Meal_ItemsRepository;

@Service
public class Meal_ItemsService {
    @Autowired
    Meal_ItemsRepository repo;
    
    @Autowired
    Data_IBGEService foodService;

    public Meal_Items_Key registerMeal_Items(Meal meal, Integer foodId, double weight) {
        // Adicione logs para depuração
        System.out.println("Meal ID: " + meal.getIdMeal());
        System.out.println("Food ID: " + foodId);
        System.out.println("Weight: " + weight);
    
        Meal_Items newItems = new Meal_Items();
        Meal_Items_Key itemKey = new Meal_Items_Key();
        itemKey.setIdMeal(meal.getIdMeal());
        itemKey.setIdFood(foodId);
    
        newItems.setId_item(itemKey);
        newItems.setIdMeal(meal);
        newItems.setIdFood(foodService.showOne(foodId));
        newItems.setWeight(weight);
    
        Meal_Items savedItems = repo.save(newItems);
        return savedItems.getId_item();
    }
    

    public List<Meal_ItemsDTO> getMealDetails(Integer idMeal){
        List<Object[]> results = repo.getMealDetails(idMeal);
        List<Meal_ItemsDTO> dtoList = new ArrayList<>();

        for (Object[] result : results){
            Meal_ItemsDTO dto = new Meal_ItemsDTO(
                (Integer) result[0],  // idMeal
                (Date) result[1],  // mealDate
                (Time) result[2],  // mealTime
                (Integer) result[3],   // id_item
                (String) result[4],   // foodName
                (String) result[5],   // preparationMethod
                (Double) result[6],  // weight
                (BigDecimal) result[7],   // energy_KCal
                (BigDecimal) result[8],  // carbohydrates_g
                (BigDecimal) result[9],  // protein_g
                (BigDecimal) result[10],  // total_fats_g
                (BigDecimal) result[11],  // satured_fats_g
                (BigDecimal) result[12],  // trans_fats_g
                (BigDecimal) result[13],  // dietary_fiber_g
                (BigDecimal) result[14],  // sodium_g
                (BigDecimal) result[15]  // total_sugars_g
            );
        dtoList.add(dto);
        }
        return dtoList;
    }
}
