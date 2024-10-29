package com.solo.api.DTO.diet;

import java.util.Objects;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.Time;

public class MealSummaryDTO {
private Integer idMeal;
    private Date mealDate;
    private Time mealTime;
    private Double totalWeight = 0.0;
    private BigDecimal totalEnergyKCal = BigDecimal.ZERO;
    private BigDecimal totalCarbohydrates = BigDecimal.ZERO;
    private BigDecimal totalProteins = BigDecimal.ZERO;
    private BigDecimal totalFats = BigDecimal.ZERO;
    private BigDecimal totalSaturatedFats = BigDecimal.ZERO;
    private BigDecimal totalTransFats = BigDecimal.ZERO;
    private BigDecimal totalDietaryFiber = BigDecimal.ZERO;
    private BigDecimal totalSodium = BigDecimal.ZERO;
    private BigDecimal totalSugars = BigDecimal.ZERO;

    // Construtor padrão
    public MealSummaryDTO() {}

    // Construtor com todos os atributos
    public MealSummaryDTO(Integer idMeal, Date mealDate, Time mealTime, Double totalWeight,
                          BigDecimal totalEnergyKCal, BigDecimal totalCarbohydrates, 
                          BigDecimal totalProteins, BigDecimal totalFats, 
                          BigDecimal totalSaturatedFats, BigDecimal totalTransFats, 
                          BigDecimal totalDietaryFiber, BigDecimal totalSodium, 
                          BigDecimal totalSugars) {
        this.idMeal = idMeal;
        this.mealDate = mealDate;
        this.mealTime = mealTime;
        this.totalWeight = totalWeight;
        this.totalEnergyKCal = totalEnergyKCal;
        this.totalCarbohydrates = totalCarbohydrates;
        this.totalProteins = totalProteins;
        this.totalFats = totalFats;
        this.totalSaturatedFats = totalSaturatedFats;
        this.totalTransFats = totalTransFats;
        this.totalDietaryFiber = totalDietaryFiber;
        this.totalSodium = totalSodium;
        this.totalSugars = totalSugars;
    }

    // Getters e Setters
    public Integer getIdMeal() {
        return idMeal;
    }
    public void setIdMeal(Integer idMeal) {
        this.idMeal = idMeal;
    }
    public Date getMealDate() {
        return mealDate;
    }
    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }
    public Time getMealTime() {
        return mealTime;
    }
    public void setMealTime(Time mealTime) {
        this.mealTime = mealTime;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }
    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalEnergyKCal() {
        return totalEnergyKCal;
    }
    public void setTotalEnergyKCal(BigDecimal totalEnergyKCal) {
        this.totalEnergyKCal = totalEnergyKCal;
    }

    public BigDecimal getTotalCarbohydrates() {
        return totalCarbohydrates;
    }
    public void setTotalCarbohydrates(BigDecimal totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public BigDecimal getTotalProteins() {
        return totalProteins;
    }
    public void setTotalProteins(BigDecimal totalProteins) {
        this.totalProteins = totalProteins;
    }

    public BigDecimal getTotalFats() {
        return totalFats;
    }
    public void setTotalFats(BigDecimal totalFats) {
        this.totalFats = totalFats;
    }

    public BigDecimal getTotalSaturatedFats() {
        return totalSaturatedFats;
    }
    public void setTotalSaturatedFats(BigDecimal totalSaturatedFats) {
        this.totalSaturatedFats = totalSaturatedFats;
    }

    public BigDecimal getTotalTransFats() {
        return totalTransFats;
    }
    public void setTotalTransFats(BigDecimal totalTransFats) {
        this.totalTransFats = totalTransFats;
    }

    public BigDecimal getTotalDietaryFiber() {
        return totalDietaryFiber;
    }
    public void setTotalDietaryFiber(BigDecimal totalDietaryFiber) {
        this.totalDietaryFiber = totalDietaryFiber;
    }

    public BigDecimal getTotalSodium() {
        return totalSodium;
    }
    public void setTotalSodium(BigDecimal totalSodium) {
        this.totalSodium = totalSodium;
    }

    public BigDecimal getTotalSugars() {
        return totalSugars;
    }
    public void setTotalSugars(BigDecimal totalSugars) {
        this.totalSugars = totalSugars;
    }

    @Override
    public String toString() {
        return "MealSummaryDTO{" +
                "idMeal=" + idMeal +
                ", mealDate=" + mealDate +
                ", mealTime=" + mealTime +
                ", totalWeight=" + totalWeight +
                ", totalEnergyKCal=" + totalEnergyKCal +
                ", totalCarbohydrates=" + totalCarbohydrates +
                ", totalProteins=" + totalProteins +
                ", totalFats=" + totalFats +
                ", totalSaturatedFats=" + totalSaturatedFats +
                ", totalTransFats=" + totalTransFats +
                ", totalDietaryFiber=" + totalDietaryFiber +
                ", totalSodium=" + totalSodium +
                ", totalSugars=" + totalSugars +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeal, mealDate, mealTime, totalWeight, totalEnergyKCal, totalCarbohydrates,
                            totalProteins, totalFats, totalSaturatedFats, totalTransFats,
                            totalDietaryFiber, totalSodium, totalSugars);
    }
}
