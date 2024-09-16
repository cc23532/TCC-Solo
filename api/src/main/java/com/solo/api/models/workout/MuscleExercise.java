package com.solo.api.models.workout;

import com.solo.api.models.user.SoloUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MuscleExercise", schema = "appSolo")
public class MuscleExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idExercise;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private SoloUser user;

    @Column(nullable = false)
    private String name;


    public MuscleExercise(){

    }

    public MuscleExercise(Integer idExercise, SoloUser user, String name){
        this.idExercise = idExercise;
        this.user = user;
        this.name = name;
    }

    public Integer getIdExercise() {
        return idExercise;
    }
    
    public SoloUser getUser() {
        return user;
    }

    public void setUser(SoloUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
