package com.myhrcrmproject.dto.contractDTO;

import com.myhrcrmproject.domain.enums.ContractType;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for contract details response")
public class ContractResponseDTO {

    @Schema(description = "Contract ID", example = "1")
    private Integer id;

    @Schema(description = "Start date of the contract", example = "2023-01-01")
    private LocalDate startDate;

    @Schema(description = "End date of the contract", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Salary", example = "50000.0")
    private Double salary;

    @Schema(description = "Type of contract", example = "FULL_TIME")
    private ContractType contractType;

    @Schema(description = "Details of the associated candidate")
    private CandidateShortResponseDTO candidate;

    @Schema(description = "Details of the associated employee")
    private EmployeeShortResponseDTO employee;

    @Schema(description = "Details of the associated client")
    private ClientShortResponseDTO client;
}
