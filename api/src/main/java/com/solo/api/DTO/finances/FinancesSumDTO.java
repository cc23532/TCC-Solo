package com.solo.api.DTO.finances;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FinancesSumDTO {
    private Integer idUser;
    private Double totalIncomes;
    private Double totalExpenses;
    
}
