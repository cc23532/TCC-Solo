package com.solo.api.services.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Double totalWeight =0.0;
        BigDecimal totalEnergyKCal = BigDecimal.valueOf(0.0);
        BigDecimal totalProteins = BigDecimal.valueOf(0.0);
        BigDecimal totalFats = BigDecimal.valueOf(0.0);
        BigDecimal totalCarbohydrates = BigDecimal.valueOf(0.0);
        BigDecimal totalSaturatedFats = BigDecimal.valueOf(0.0);
        BigDecimal totalTransFats = BigDecimal.valueOf(0.0);
        BigDecimal totalDietaryFiber = BigDecimal.valueOf(0.0);
        BigDecimal totalSodium = BigDecimal.valueOf(0.0);
        BigDecimal totalSugars = BigDecimal.valueOf(0.0);
    
        if (mealItems.isEmpty()) {
            return new MealSummaryDTO(null, null, null, 0.0, BigDecimal.ZERO, BigDecimal.ZERO, 
                                       BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 
                                       BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 
                                       BigDecimal.ZERO);
        }
    
        for (Meal_ItemsDTO item : mealItems) {
            if (item != null) { // Verifica se o item não é null
                totalWeight += item.getWeight();
                totalEnergyKCal = totalEnergyKCal.add(item.getEnergy_KCal() != null ? item.getEnergy_KCal() : BigDecimal.ZERO);
                totalProteins = totalProteins.add(item.getProtein_g() != null ? item.getProtein_g() : BigDecimal.ZERO);
                totalFats = totalFats.add(item.getTotal_fats_g() != null ? item.getTotal_fats_g() : BigDecimal.ZERO);
                totalCarbohydrates = totalCarbohydrates.add(item.getCarbohydrates_g() != null ? item.getCarbohydrates_g() : BigDecimal.ZERO);
                totalSaturatedFats = totalSaturatedFats.add(item.getSatured_fats_g() != null ? item.getSatured_fats_g() : BigDecimal.ZERO);
                totalTransFats = totalTransFats.add(item.getTrans_fats_g() != null ? item.getTrans_fats_g() : BigDecimal.ZERO);
                totalDietaryFiber = totalDietaryFiber.add(item.getDietary_fiber_g() != null ? item.getDietary_fiber_g() : BigDecimal.ZERO);
                totalSodium = totalSodium.add(item.getSodium_g() != null ? item.getSodium_g() : BigDecimal.ZERO);
                totalSugars = totalSugars.add(item.getTotal_sugars_g() != null ? item.getTotal_sugars_g() : BigDecimal.ZERO);
            }
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
