package com.solo.api.repositories.schedule;

import com.solo.api.models.schedule.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
}
