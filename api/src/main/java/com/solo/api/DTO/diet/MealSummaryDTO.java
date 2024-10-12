package com.solo.api.DTO.diet;

import java.util.Objects;
import java.util.Date;
import java.sql.Time;

public class MealSummaryDTO {
    private Integer idMeal;                     // ID da refeição
    private Date mealDate;                       // Data da refeição
    private Time mealTime;                       // Hora da refeição
    private double totalWeight;                  // Peso total dos itens
    private double totalEnergyKCal;              // Energia total em KCal
    private double totalCarbohydrates;           // Carboidratos totais
    private double totalProteins;                // Proteínas totais
    private double totalFats;                    // Gorduras totais
    private double totalSaturatedFats;           // Gorduras saturadas totais
    private double totalTransFats;               // Gorduras trans totais
    private double totalDietaryFiber;            // Fibras dietéticas totais
    private double totalSodium;                  // Sódio total
    private double totalSugars;                  // Açúcares totais

    // Construtor padrão
    public MealSummaryDTO() {}

    // Construtor com todos os atributos
    public MealSummaryDTO(Integer idMeal, Date mealDate, Time mealTime, double totalWeight,
                          double totalEnergyKCal, double totalCarbohydrates, 
                          double totalProteins, double totalFats, 
                          double totalSaturatedFats, double totalTransFats, 
                          double totalDietaryFiber, double totalSodium, 
                          double totalSugars) {
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

    public double getTotalWeight() {
        return totalWeight;
    }
    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalEnergyKCal() {
        return totalEnergyKCal;
    }
    public void setTotalEnergyKCal(double totalEnergyKCal) {
        this.totalEnergyKCal = totalEnergyKCal;
    }

    public double getTotalCarbohydrates() {
        return totalCarbohydrates;
    }
    public void setTotalCarbohydrates(double totalCarbohydrates) {
        this.totalCarbohydrates = totalCarbohydrates;
    }

    public double getTotalProteins() {
        return totalProteins;
    }
    public void setTotalProteins(double totalProteins) {
        this.totalProteins = totalProteins;
    }

    public double getTotalFats() {
        return totalFats;
    }
    public void setTotalFats(double totalFats) {
        this.totalFats = totalFats;
    }

    public double getTotalSaturatedFats() {
        return totalSaturatedFats;
    }
    public void setTotalSaturatedFats(double totalSaturatedFats) {
        this.totalSaturatedFats = totalSaturatedFats;
    }

    public double getTotalTransFats() {
        return totalTransFats;
    }
    public void setTotalTransFats(double totalTransFats) {
        this.totalTransFats = totalTransFats;
    }

    public double getTotalDietaryFiber() {
        return totalDietaryFiber;
    }
    public void setTotalDietaryFiber(double totalDietaryFiber) {
        this.totalDietaryFiber = totalDietaryFiber;
    }

    public double getTotalSodium() {
        return totalSodium;
    }
    public void setTotalSodium(double totalSodium) {
        this.totalSodium = totalSodium;
    }

    public double getTotalSugars() {
        return totalSugars;
    }
    public void setTotalSugars(double totalSugars) {
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MealSummaryDTO that = (MealSummaryDTO) obj;
        return Double.compare(that.totalWeight, totalWeight) == 0 &&
               Double.compare(that.totalEnergyKCal, totalEnergyKCal) == 0 &&
               Double.compare(that.totalCarbohydrates, totalCarbohydrates) == 0 &&
               Double.compare(that.totalProteins, totalProteins) == 0 &&
               Double.compare(that.totalFats, totalFats) == 0 &&
               Double.compare(that.totalSaturatedFats, totalSaturatedFats) == 0 &&
               Double.compare(that.totalTransFats, totalTransFats) == 0 &&
               Double.compare(that.totalDietaryFiber, totalDietaryFiber) == 0 &&
               Double.compare(that.totalSodium, totalSodium) == 0 &&
               Double.compare(that.totalSugars, totalSugars) == 0 &&
               idMeal.equals(that.idMeal) &&
               mealDate.equals(that.mealDate) &&
               mealTime.equals(that.mealTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeal, mealDate, mealTime, totalWeight, totalEnergyKCal, totalCarbohydrates,
                            totalProteins, totalFats, totalSaturatedFats, totalTransFats,
                            totalDietaryFiber, totalSodium, totalSugars);
    }
}
