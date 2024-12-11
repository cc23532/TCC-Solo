package com.solo.api.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.user.UserMood;


public interface UserMoodRepository extends JpaRepository<UserMood, Integer>{

    @Query(value = "SELECT * FROM appSolo.UserMood WHERE idUser = :idUser", nativeQuery = true)
    public List<UserMood> findMoodByIdUser(@Param("idUser") Integer idUser);
                            
    @Query(value = "EXEC appsolo.GetMoodCountByTimeFrame :timeFrame, :idUser", nativeQuery = true)
    public List<Object[]> getMoodCountByTimeFrame(  @Param("timeFrame") String timeFrame,
                                                    @Param("idUser") Integer idUser);

}
