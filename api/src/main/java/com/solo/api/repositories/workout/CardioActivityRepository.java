package com.solo.api.repositories.workout;

import com.solo.api.models.workout.CardioActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardioActivityRepository extends JpaRepository<CardioActivity, Integer> {
}
