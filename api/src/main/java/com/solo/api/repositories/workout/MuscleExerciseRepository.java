package com.solo.api.repositories.workout;

import com.solo.api.models.workout.MuscleExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuscleExerciseRepository extends JpaRepository<MuscleExercise,Integer> {
}
