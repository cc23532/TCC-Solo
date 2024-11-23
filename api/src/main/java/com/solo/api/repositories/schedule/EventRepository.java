package com.solo.api.repositories.schedule;

import com.solo.api.models.schedule.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT * FROM appSolo.Event WHERE idUser = :idUser", nativeQuery = true)
    public List<Event> getEventsByIdUser(@Param("idUser") Integer idUser);

    @Query(value = "SELECT * FROM appSolo.Event WHERE idUser = :idUser AND eventDate = :eventDate ORDER BY startTime ASC", nativeQuery = true)
    List<Event> getEventsByUserAndDate(@Param("idUser") Integer idUser, @Param("eventDate") Date eventDate);

}
