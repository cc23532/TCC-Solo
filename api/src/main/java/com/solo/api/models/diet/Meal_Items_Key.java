package com.solo.api.models.diet;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Meal_Items_Key {

    @Column(name = "idMeal")
    private Integer idMeal;

    @Column(name = "idFood")
    private Integer idFood;
    
    public Meal_Items_Key(){

    }

    public Meal_Items_Key(Integer idMeal, Integer idFood){
        this.idMeal = idMeal;
        this.idFood  = idMeal;
    }

    public Integer getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(Integer idMeal) {
        this.idMeal = idMeal;
    }

    public Integer getIdFood() {
        return idFood;
    }

    public void setIdFood(Integer idFood) {
        this.idFood = idFood;
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
