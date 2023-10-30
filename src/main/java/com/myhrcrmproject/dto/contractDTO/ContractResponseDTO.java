package com.myhrcrmproject.dto.contractDTO;

import com.myhrcrmproject.domain.enums.ContractType;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractResponseDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private ContractType contractType;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
    private ClientShortResponseDTO client;
}
