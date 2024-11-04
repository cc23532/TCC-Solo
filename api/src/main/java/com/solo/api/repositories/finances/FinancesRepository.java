package com.solo.api.repositories.finances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solo.api.models.finances.Finances;
import com.solo.api.models.user.SoloUser;

import java.util.List;
import java.util.Date;

public interface FinancesRepository extends JpaRepository<Finances, Integer>{
    
    @Query(value = "EXEC appSolo.GetFinancesByDateRange :startDate, :endDate, :idUser", nativeQuery = true)
    public List<Finances> getExtractByPeriodAndUser(@Param("startDate") Date startDate, 
                                                    @Param("endDate") Date endDate,
                                                    @Param("idUser") Integer idUser);

    @Query(value = "SELECT " +
                        "idActivity, " +
                        "idUser, " +
                        "transactionType, " +
                        "movementType, " +
                        "moneyValue, " +
                        "activityDate, " +
                        "label, " +
                        "description" +
                    "FROM " + 
                        "appSolo.Finances " +
                    "WHERE " +
                        "transactionType = :transactionType AND idUser = :idUser;", nativeQuery = true)
    public List<Finances> getFinancesByTransactionType( @Param("transactionType") String transactionType,
                                                        @Param("idUser") SoloUser idUser);

    @Query(value = "EXEC appSolo.GetFinancesSumByTimeFrame :timeFrame, :idUser", nativeQuery = true)
    public List<Object[]> getSumByTimeFrame(@Param("timeFrame") String timeFrame,
                                            @Param("idUser") Integer idUser);
}
