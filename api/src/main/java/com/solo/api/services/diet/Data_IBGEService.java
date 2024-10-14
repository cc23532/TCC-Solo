package com.solo.api.services.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.api.models.diet.Data_IBGE;
import com.solo.api.repositories.diet.Data_IBGERepository;

import java.util.List;
import java.util.Optional;

@Service
public class Data_IBGEService {
    @Autowired
    Data_IBGERepository repo;

    public Optional<Data_IBGE> findByNameAndMethod(String descricao_do_alimento, String descricao_da_preparacao){
        return repo.findByNameAndMethod(descricao_do_alimento, descricao_da_preparacao);
    }
    public Optional<Data_IBGE> findById(Integer id){
        return repo.findById(id);
    }

    public List<Data_IBGE> findByName(String descricao_do_alimento){
        return repo.findByName(descricao_do_alimento);
    }
    
    public List<Data_IBGE> findByCategory(String category){
        return repo.findByCategory(category);
    }

    public List<Data_IBGE> showAll(){
        return repo.findAll();
    }

    public Data_IBGE showOne(Integer id){
        return repo.findByIdFood(id);
    }
}
