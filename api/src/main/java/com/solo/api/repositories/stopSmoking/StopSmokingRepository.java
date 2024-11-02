package com.solo.api.repositories.stopSmoking;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.DTO.stopSmoking.StopSmokingSummaryDTO;
import com.solo.api.models.stopSmoking.StopSmoking;
import com.solo.api.models.user.SoloUser;

public interface StopSmokingRepository extends JpaRepository<StopSmoking, Integer>{
 
    @Query(value = "SELECT * FROM appSolo.StopSmokingSummary WHERE idUser = :idUser", nativeQuery = true)
    public Optional<StopSmokingSummaryDTO> getSummaryByUser(@Param("idUser") SoloUser idUser);
}
