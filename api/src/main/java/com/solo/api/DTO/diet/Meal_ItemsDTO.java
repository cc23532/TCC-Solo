package com.solo.api.DTO.diet;

import java.util.Date;
import java.math.BigDecimal;
import java.sql.Time;

public class Meal_ItemsDTO {
    private Integer idMeal;
    private Date mealDate;
    private Time mealTime;
    private Integer id_item;
    private String foodName;
    private String preparationMethod;
    private Double weight;
    private BigDecimal energy_KCal;
    private BigDecimal carbohydrates_g;
    private BigDecimal protein_g;
    private BigDecimal total_fats_g;
    private BigDecimal satured_fats_g;
    private BigDecimal trans_fats_g;
    private BigDecimal dietary_fiber_g;
    private BigDecimal sodium_g;
    private BigDecimal total_sugars_g;

    public Meal_ItemsDTO() {}

    public Meal_ItemsDTO(Integer idMeal, Date mealDate, Time mealTime, Integer id_item,
                         String foodName, String preparationMethod, Double weight, BigDecimal energy_KCal,
                         BigDecimal carbohydrates_g, BigDecimal protein_g, BigDecimal total_fats_g,
                         BigDecimal satured_fats_g, BigDecimal trans_fats_g, BigDecimal dietary_fiber_g,
                         BigDecimal sodium_g, BigDecimal total_sugars_g) {
        this.idMeal = idMeal;
        this.mealDate = mealDate;
        this.mealTime = mealTime;
        this.id_item = id_item;
        this.foodName = foodName;
        this.preparationMethod = preparationMethod;
        this.weight = weight;
        this.energy_KCal = energy_KCal;
        this.carbohydrates_g = carbohydrates_g;
        this.protein_g = protein_g;
        this.total_fats_g = total_fats_g;
        this.satured_fats_g = satured_fats_g;
        this.trans_fats_g = trans_fats_g;
        this.dietary_fiber_g = dietary_fiber_g;
        this.sodium_g = sodium_g;
        this.total_sugars_g = total_sugars_g;
    }

    // Getters e Setters
    public Integer getIdMeal() { return idMeal; }
    public void setIdMeal(Integer idMeal) { this.idMeal = idMeal; }

    public Date getMealDate() { return mealDate; }
    public void setMealDate(Date mealDate) { this.mealDate = mealDate; }

    public Time getMealTime() { return mealTime; }
    public void setMealTime(Time mealTime) { this.mealTime = mealTime; }

    public Integer getId_item() { return id_item; }
    public void setId_item(Integer id_item) { this.id_item = id_item; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getPreparationMethod() { return preparationMethod; }
    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public BigDecimal getEnergy_KCal() { return energy_KCal; }
    public void setEnergy_KCal(BigDecimal energy_KCal) { this.energy_KCal = energy_KCal; }

    public BigDecimal getCarbohydrates_g() { return carbohydrates_g; }
    public void setCarbohydrates_g(BigDecimal carbohydrates_g) {
        this.carbohydrates_g = carbohydrates_g;
    }

    public BigDecimal getProtein_g() { return protein_g; }
    public void setProtein_g(BigDecimal protein_g) { this.protein_g = protein_g; }

    public BigDecimal getTotal_fats_g() { return total_fats_g; }
    public void setTotal_fats_g(BigDecimal total_fats_g) { this.total_fats_g = total_fats_g; }

    public BigDecimal getSatured_fats_g() { return satured_fats_g; }
    public void setSatured_fats_g(BigDecimal satured_fats_g) {
        this.satured_fats_g = satured_fats_g;
    }

    public BigDecimal getTrans_fats_g() { return trans_fats_g; }
    public void setTrans_fats_g(BigDecimal trans_fats_g) { this.trans_fats_g = trans_fats_g; }

    public BigDecimal getDietary_fiber_g() { return dietary_fiber_g; }
    public void setDietary_fiber_g(BigDecimal dietary_fiber_g) {
        this.dietary_fiber_g = dietary_fiber_g;
    }

    public BigDecimal getSodium_g() { return sodium_g; }
    public void setSodium_g(BigDecimal sodium_g) { this.sodium_g = sodium_g; }

    public BigDecimal getTotal_sugars_g() { return total_sugars_g; }
    public void setTotal_sugars_g(BigDecimal total_sugars_g) {
        this.total_sugars_g = total_sugars_g;
    }

    @Override
    public String toString() {
        return "Meal_ItemsDTO{" +
                "idMeal=" + idMeal +
                ", mealDate='" + mealDate + '\'' +
                ", mealTime='" + mealTime + '\'' +
                ", id_item=" + id_item +
                ", foodName='" + foodName + '\'' +
                ", preparationMethod='" + preparationMethod + '\'' +
                ", weight=" + weight +
                ", energy_KCal=" + energy_KCal +
                ", carbohydrates_g=" + carbohydrates_g +
                ", protein_g=" + protein_g +
                ", total_fats_g=" + total_fats_g +
                ", satured_fats_g=" + satured_fats_g +
                ", trans_fats_g=" + trans_fats_g +
                ", dietary_fiber_g=" + dietary_fiber_g +
                ", sodium_g=" + sodium_g +
                ", total_sugars_g=" + total_sugars_g +
                '}';
    }
}
