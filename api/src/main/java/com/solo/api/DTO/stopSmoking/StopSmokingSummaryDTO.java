package com.solo.api.DTO.stopSmoking;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StopSmokingSummaryDTO {
    private int idCount;
    private int idUser;
    private LocalDateTime baseDate;
    private int daysWithoutSmoking;
    private int avoidedCigarettes;
    private double moneySaved;
    private double lifeMinutesSaved;
}
