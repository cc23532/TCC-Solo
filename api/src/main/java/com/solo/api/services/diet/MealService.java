package com.solo.api.services.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.solo.api.DTO.diet.MealSummaryDTO;
import com.solo.api.DTO.diet.Meal_ItemsDTO;
import com.solo.api.models.diet.Meal;
import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.diet.MealRepository;

@Service
public class MealService {
    @Autowired
    MealRepository repo;

    @Autowired
    Meal_ItemsService itemsService;

    public int registerNewMeal(SoloUser user, Date mealDate, Time mealTime){
        Meal newMeal = new Meal();
        newMeal.setUser(user);
        newMeal.setMealDate(mealDate);
        newMeal.setMealTime(mealTime);

        Meal savedMeal = repo.save(newMeal);
        return savedMeal.getIdMeal();
    }

    public List<Meal> findByUser(Integer idUser){
        return repo.findByUser(idUser);
    }

    private MealSummaryDTO calculateMealSummary(List<Meal_ItemsDTO> mealItems) {
        double totalWeight = 0;
        double totalEnergyKCal = 0;
        double totalProteins = 0;
        double totalFats = 0;
        double totalCarbohydrates = 0;
        double totalSaturatedFats = 0;
        double totalTransFats = 0;
        double totalDietaryFiber = 0;
        double totalSodium = 0;
        double totalSugars = 0;

        if (mealItems.isEmpty()) {
            return new MealSummaryDTO(null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        for (Meal_ItemsDTO item : mealItems) {
            totalWeight += item.getWeight();
            totalEnergyKCal += item.getEnergy_KCal();
            totalProteins += item.getProtein_g();
            totalFats += item.getTotal_fats_g();
            totalCarbohydrates += item.getCarbohydrates_g();
            totalSaturatedFats += item.getSatured_fats_g();
            totalTransFats += item.getTrans_fats_g();
            totalDietaryFiber += item.getDietary_fiber_g();
            totalSodium += item.getSodium_g();
            totalSugars += item.getTotal_sugars_g();
        }

        // Pega as informações da refeição do primeiro item
        Meal_ItemsDTO firstItem = mealItems.get(0);
        Integer idMealRef = firstItem.getIdMeal(); // ID da refeição
        Date mealDate = firstItem.getMealDate();     // Data da refeição
        Time mealTime = firstItem.getMealTime();     // Hora da refeição
        
        // Cria e retorna o resumo da refeição
        return new MealSummaryDTO(
                idMealRef, mealDate, mealTime,
                totalWeight, totalEnergyKCal, totalCarbohydrates, 
                totalProteins, totalFats, totalSaturatedFats, 
                totalTransFats, totalDietaryFiber, totalSodium, 
                totalSugars
        );
    }

    private MealSummaryDTO getMealSummary(Integer idMeal) {
        // Obtém os detalhes da refeição
        List<Meal_ItemsDTO> mealItems = itemsService.getMealDetails(idMeal);

        // Calcula e retorna o resumo da refeição usando os detalhes obtidos
        return calculateMealSummary(mealItems);
    }

    public List<MealSummaryDTO> getMealsByUser(Integer idUser){
        // 1. Obtenha todas as refeições do usuário
        List<Meal> meals = repo.findByUser(idUser); // Presumindo que exista esse método no repositório

        // 2. Crie uma lista para armazenar os resumos das refeições
        List<MealSummaryDTO> mealSummaries = new ArrayList<>();

        // 3. Para cada refeição, obtenha o resumo e adicione à lista
        for (Meal meal : meals) {
            MealSummaryDTO mealSummary = getMealSummary(meal.getIdMeal());
            mealSummaries.add(mealSummary);
        }
        // 4. Retorne a lista de resumos das refeições
        return mealSummaries;
    }
}
