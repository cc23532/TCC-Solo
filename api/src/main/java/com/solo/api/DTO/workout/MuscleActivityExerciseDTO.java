package com.solo.api.DTO.workout;

public class MuscleActivityExerciseDTO {
    private Integer id_item;
    private Integer idActivity;
    private Integer idExercise;    
    private String category;
    private String name;
    private double weight;
    private int series;
    private int repetition;

    public MuscleActivityExerciseDTO(Integer id_item, Integer idActivity, Integer idExercise, String category, String name, double weight, int series, int repetition) {
        this.id_item = id_item;
        this.idActivity = idActivity;
        this.idExercise = idExercise;
        this.category = category;
        this.name = name;
        this.weight = weight;
        this.series = series;
        this.repetition = repetition;
    }

    // Getters e Setters
    public Integer getId_item() { return id_item; }
    public void setId_item(Integer id_item) { this.id_item = id_item; }

    public Integer getIdActivity() { return idActivity; }
    public void setIdActivity(Integer idActivity) { this.idActivity = idActivity; }

    public Integer getIdExercise() { return idExercise; }
    public void setIdExercise(Integer idExercise) { this.idExercise = idExercise; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getRepetition() { return repetition; }
    public void setRepetition(int repetition) { this.repetition = repetition; }
}
