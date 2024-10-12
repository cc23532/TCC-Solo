package com.solo.api.models.diet;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.ForeignKey;

@Entity
@Table(name = "Meal_Items", schema = "appSolo")
public class Meal_Items {
    
    @EmbeddedId
    private Meal_Items_Key id_item;

    @ManyToOne
    @MapsId("idMeal")
    @JoinColumn(name = "idMeal", foreignKey = @ForeignKey(name = "FK_Meal_Items_Meal"))
    private Meal idMeal;

    @ManyToOne
    @MapsId("idFood")
    @JoinColumn(name = "idFood", foreignKey = @ForeignKey(name = "FK_Meal_Items_FoodData"))
    private Data_IBGE idFood;

    @Column(nullable = false)
    private double weight;

    public Meal_Items(){

    }

    public Meal_Items(Meal idMeal, Data_IBGE idFood, double weight){
        this.idMeal = idMeal;
        this.idFood = idFood;
        this.weight = weight;
    }

    public Meal_Items_Key getId_item() {
        return id_item;
    }

    public Meal getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(Meal idMeal) {
        this.idMeal = idMeal;
    }

    public Data_IBGE getIdFood() {
        return idFood;
    }

    public void setIdFood(Data_IBGE idFood) {
        this.idFood = idFood;
    }

    public double getWeight() {
        return weight;
    }   

    public void setWeight(double weight) {
        this.weight = weight;
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
