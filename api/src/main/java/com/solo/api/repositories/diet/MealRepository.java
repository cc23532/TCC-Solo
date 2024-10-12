package com.solo.api.repositories.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import com.solo.api.models.diet.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    
    @Query(value = "SELECT * FROM appSolo.Meal where idUser = :idUser", nativeQuery = true)
    public List<Meal> findByUser(@Param("idUser") Integer idUser);
}
