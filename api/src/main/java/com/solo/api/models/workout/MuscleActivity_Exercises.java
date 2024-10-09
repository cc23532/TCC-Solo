package com.solo.api.models.workout;

import jakarta.persistence.*;

@Entity
@Table(name = "MuscleActivity_Exercises", schema = "appSolo")
public class MuscleActivity_Exercises {

    @EmbeddedId
    private MuscleAc_Ex_ItemKey idMuscleActivity_Exercise;

    @ManyToOne
    @MapsId("idActivity")
    @JoinColumn(name = "idActivity", foreignKey = @ForeignKey(name = "FK_MuscleAcEx_Activity"))
    private MuscleActivity activity;

    @ManyToOne
    @MapsId("idExercise")
    @JoinColumn(name = "idExercise", foreignKey = @ForeignKey(name = "FK_MuscleAcEx_Exercise"))
    private MuscleExercise exercise;

    @Column
    private String name;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private int series;

    @Column(nullable = false)
    private int repetition;

    public MuscleActivity_Exercises(){
        
    }

    public MuscleActivity_Exercises(MuscleActivity activity, MuscleExercise exercise, String name, double weight, int series, int repetition){
        this.activity = activity;
        this.exercise = exercise;
        this.name = name;
        this.weight = weight;
        this.series = series;
        this.repetition = repetition;
    }

    public MuscleAc_Ex_ItemKey getIdMuscleActivity_Exercise() {
        return idMuscleActivity_Exercise;
    }

    public void setIdMuscleActivity_Exercise(MuscleAc_Ex_ItemKey idMuscleActivity_Exercise) {
        this.idMuscleActivity_Exercise = idMuscleActivity_Exercise;
    }

    public MuscleActivity getActivity() {
        return activity;
    }

    public Integer getIdActivity() {
        return activity.getIdActivity();
    }


    public void setActivity(MuscleActivity activity) {
        this.activity = activity;
    }

    public MuscleExercise getExercise() {
        return exercise;
    }

    public Integer getIdExercise() {
        return exercise.getIdExercise();
    }

    public void setExercise(MuscleExercise exercise) {
        this.exercise = exercise;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
