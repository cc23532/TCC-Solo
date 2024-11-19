package com.solo.api.repositories.schedule;

import com.solo.api.models.schedule.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
