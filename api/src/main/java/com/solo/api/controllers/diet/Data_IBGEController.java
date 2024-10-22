package com.solo.api.controllers.diet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.diet.Data_IBGE;
import com.solo.api.models.diet.Food_Select;
import com.solo.api.repositories.diet.Data_IBGERepository;
import com.solo.api.repositories.diet.Food_SelectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RestController
@RequestMapping("/diet/food-data-ibge")
public class Data_IBGEController {
    @Autowired
    Data_IBGERepository repo;

    @Autowired
    Food_SelectRepository selectRepo;
    
    @GetMapping
    public List<Data_IBGE> all() {
        return repo.findAll();
    }

    // Listagem de alimentos para select ao criar nova refeição
    @GetMapping("/select-food")
    public List<Food_Select> allFoodForSelect(){
        return selectRepo.findAll();
    }
    
}
