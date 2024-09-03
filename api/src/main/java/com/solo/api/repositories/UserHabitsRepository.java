package com.solo.api.repositories;

import com.solo.api.models.UserHabits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHabitsRepository extends JpaRepository<Integer, UserHabits> {
}
