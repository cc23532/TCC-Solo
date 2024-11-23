package com.solo.api.DTO.stopSmoking;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StopSmokingSummaryDTO {
    private Integer idCount;
    private Integer idUser;
    private String baseDate;
    private Map<String, Integer> daysWithoutSmoking;
    private String avoidedCigarettes;
    private String moneySaved;
    private Map<String, Integer> lifeMinutesSaved;

    public StopSmokingSummaryDTO(Integer idCount, Integer idUser, String baseDate, Map<String, Integer> daysWithoutSmoking, Double avoidedCigarettes, Double moneySaved, Map<String, Integer> lifeMinutesSaved){
        this.idCount = idCount;
        this.idUser = idUser;
        this.baseDate = baseDate;
        this.daysWithoutSmoking = daysWithoutSmoking;
        this.avoidedCigarettes = String.valueOf(avoidedCigarettes.intValue()); // Sem casas decimais
        this.moneySaved = String.format("%.2f", moneySaved); // Duas casas decimais
        this.lifeMinutesSaved = lifeMinutesSaved;
    }
}
