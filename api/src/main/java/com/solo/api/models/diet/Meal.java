package com.solo.api.models.diet;

import com.solo.api.models.user.SoloUser;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Meal", schema = "appSolo")
public class Meal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMeal;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private SoloUser user;

    @Column(nullable = false)
    private Date mealDate;

    @Column(nullable = false)
    private Time mealTime;

    public Meal(){

    }

    public Meal(Integer idMeal, SoloUser user, Date mealDate, Time mealTime){
        this.idMeal = idMeal;
        this.user = user;
        this.mealDate = mealDate;
        this.mealTime = mealTime;
    }

    public Integer getIdMeal() {
        return idMeal;
    }

    public SoloUser getUser() {
        return user;
    }

    public void setUser(SoloUser user) {
        this.user = user;
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
