package com.solo.api.models.diet;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Meal_Items_Key {

    @Column(name = "idMeal")
    private Integer idMeal;

    @Column(name = "idUser")
    private Integer idUser;
    
    public Meal_Items_Key(){

    }

    public Meal_Items_Key(Integer idMeal, Integer idUser){
        this.idMeal = idMeal;
        this.idUser  = idMeal;
    }

    public Integer getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(Integer idMeal) {
        this.idMeal = idMeal;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
