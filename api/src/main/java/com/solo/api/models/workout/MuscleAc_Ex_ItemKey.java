package com.solo.api.models.workout;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MuscleAc_Ex_ItemKey {

    @Column(name = "idActivity")
    private Integer idActivity;

    @Column(name = "idExercise")
    private Integer idExercise;

    public MuscleAc_Ex_ItemKey(Integer idActivity, Integer idExercise){
        this.idActivity = idActivity;
        this.idExercise = idExercise;
    }
    
}
