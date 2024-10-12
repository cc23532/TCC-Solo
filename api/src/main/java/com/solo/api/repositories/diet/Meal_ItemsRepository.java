package com.solo.api.repositories.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.solo.api.models.diet.Meal_Items;
import com.solo.api.models.diet.Meal_Items_Key;

public interface Meal_ItemsRepository extends JpaRepository<Meal_Items, Meal_Items_Key>{

    @Query(value =  "SELECT * FROM appSolo.MealDetails WHERE idMeal = :idMeal", nativeQuery = true)
    public List<Object[]> getMealDetails(@Param("idMeal") Integer idMeal);
}
