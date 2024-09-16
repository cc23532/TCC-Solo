package com.solo.api.models.workout;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.ForeignKey;



public class MuscleActivity_Exercises {

    @EmbeddedId
    private MuscleAc_Ex_ItemKey idMuscleActivity_Exercise;

    @ManyToOne
    @MapsId("idActivity")
    @JoinColumn(name = "idActivity", foreignKey = @ForeignKey(name = "FK_MuscleAcEx_Activity"))
    private MuscleActivity activity;

    @ManyToOne
    @MapsId("idExercise")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "FK_MuscleAcEx_Exercise"))
    private MuscleExercise exercise;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private int series;

    @Column(nullable = false)
    private int repetition;

    public MuscleActivity_Exercises(){
        
    }

    public MuscleActivity_Exercises(MuscleActivity activity, MuscleExercise exercise, double weight, int series, int repetition){
        this.activity = activity;
        this.exercise = exercise;
        this.weight = weight;
        this.series = series;
        this.repetition = repetition;
    }

    public MuscleActivity getActivity() {
        return activity;
    }

    public void setActivity(MuscleActivity activity) {
        this.activity = activity;
    }

    public MuscleExercise getExercise() {
        return exercise;
    }

    public void setExercise(MuscleExercise exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
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
