package com.solo.api.repositories.diet;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.diet.Food_Select;

public interface Food_SelectRepository extends JpaRepository<Food_Select, Integer>{    

    @Query(value = "SELECT id_item, food_name FROM appSolo.Food_Select WHERE food_name = :food_name", nativeQuery = true)
    public Optional<Food_Select> findByFoodName(@Param("food_name") String foodName);
}