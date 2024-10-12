package com.solo.api.services.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;

import com.solo.api.models.diet.Meal;
import com.solo.api.models.diet.Meal_Items;
import com.solo.api.models.diet.Meal_Items_Key;
import com.solo.api.DTO.diet.Meal_ItemsDTO;
import com.solo.api.models.diet.Data_IBGE;
import com.solo.api.repositories.diet.Meal_ItemsRepository;

@Service
public class Meal_ItemsService {
    @Autowired
    Meal_ItemsRepository repo;

    public Meal_Items_Key registerMeal_Items(Meal meal, Data_IBGE food, double weight){
        Meal_Items newItems = new Meal_Items();
        newItems.setIdMeal(meal);
        newItems.setIdFood(food);
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
                (double) result[6],  // weight
                (double) result[7],   // energy_KCal
                (double) result[8],  // carbohydrates_g
                (double) result[9],  // protein_g
                (double) result[10],  // total_fats_g
                (double) result[11],  // satured_fats_g
                (double) result[12],  // trans_fats_g
                (double) result[13],  // dietary_fiber_g
                (double) result[14],  // sodium_g
                (double) result[15]  // total_sugars_g
            );
        dtoList.add(dto);
        }
        return dtoList;
    }
}
