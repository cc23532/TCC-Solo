package com.solo.api.models.diet;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Meal_Items_Key implements Serializable {

    @Column(name = "idMeal")
    private Integer idMeal;

    @Column(name = "idFood")
    private Integer idFood;

    // Getters e Setters
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

    // Implementar equals e hashCode para a chave composta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal_Items_Key)) return false;

        Meal_Items_Key that = (Meal_Items_Key) o;

        if (!idMeal.equals(that.idMeal)) return false;
        return idFood.equals(that.idFood);
    }

    @Override
    public int hashCode() {
        int result = idMeal.hashCode();
        result = 31 * result + idFood.hashCode();
        return result;
    }
}
