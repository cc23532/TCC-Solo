package com.solo.api.models.workout;

import jakarta.persistence.*;

@Embeddable
public class MuscleAc_Ex_ItemKey {

    @Column(name = "idActivity")
    private Integer idActivity;

    @Column(name = "idExercise")
    private Integer idExercise;

    public MuscleAc_Ex_ItemKey(){

    }

    public MuscleAc_Ex_ItemKey(Integer idActivity, Integer idExercise){
        this.idActivity = idActivity;
        this.idExercise = idExercise;
    }

    public Integer getIdActivity() {
        return idActivity;
    }

    public Integer getIdExercise() {
        return idExercise;
    }

    public void setIdActivity(Integer idActivity) {
        this.idActivity = idActivity;
    }

    public void setIdExercise(Integer idExercise) {
        this.idExercise = idExercise;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
