package com.solo.api.repositories.stopSmoking;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.stopSmoking.StopSmoking;

public interface StopSmokingRepository extends JpaRepository<StopSmoking, Integer>{
 
    @Query(value = "SELECT * FROM appSolo.StopSmokingSummary WHERE idUser = :idUser", nativeQuery = true)
    public Optional<List<Object[]>> getSummaryByUser(@Param("idUser") Integer idUser);
}
