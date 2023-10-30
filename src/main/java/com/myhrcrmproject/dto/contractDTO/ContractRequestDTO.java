package com.myhrcrmproject.dto.contractDTO;

import com.myhrcrmproject.domain.enums.ContractType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private ContractType contractType;
    private Integer candidateId;
    private Integer clientId;
}
