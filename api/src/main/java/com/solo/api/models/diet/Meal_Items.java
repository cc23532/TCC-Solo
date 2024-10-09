package com.solo.api.models.diet;

import com.solo.api.models.user.SoloUser;

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
    @MapsId("idUser")
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_Meal_Items_SoloUser"))
    private SoloUser idUser;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private String preparationMethod;

    public Meal_Items(){

    }

    public Meal_Items(Meal idMeal, SoloUser idUser, String food, String preparationMethod){
        this.idMeal = idMeal;
        this.idUser = idUser;
        this.food = food;
        this.preparationMethod = preparationMethod;
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

    public SoloUser getIdUser() {
        return idUser;
    }

    public void setIdUser(SoloUser idUser) {
        this.idUser = idUser;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
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
