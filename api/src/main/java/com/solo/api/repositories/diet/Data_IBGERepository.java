package com.solo.api.repositories.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.solo.api.models.diet.Data_IBGE;

public interface Data_IBGERepository extends JpaRepository<Data_IBGE, Integer>{

    @Query(value = "SELECT id FROM appSolo.ibge_food_data" + 
                    "WHERE descricao_do_alimento = :descricao_do_alimento AND descricao_da_preparacao = :descricao_da_preparacao", nativeQuery = true)
    public Optional<Integer> findByNameAndMethod( @Param("descricao_do_alimento") String descricao_do_alimento,
                                                @Param("descricao_da_preparacao") String descricao_da_preparacao);

    @Query(value = "SELECT id, descricao_do_alimento, Categoria, descricao_da_preparacao FROM appSolo.ibge_food_data" + 
                    "WHERE descricao_do_alimento = :descricao_do_alimento", nativeQuery = true)
    public List<Data_IBGE> findByName(  @Param("descricao_do_alimento") String descricao_do_alimento);

    @Query(value = "SELECT id, descricao_do_alimento, Categoria, descricao_da_preparacao FROM appSolo.ibge_food_data" + 
                    "WHERE categoria = :categoria", nativeQuery = true)
    public List<Data_IBGE> findByCategory(  @Param("categoria") String categoria);
}
