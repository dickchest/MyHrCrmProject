package com.myhrcrmproject.dto.contractDTO;

import com.myhrcrmproject.domain.enums.ContractType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for contract details request")
public class ContractRequestDTO {

    @Schema(description = "Start date of the contract", example = "2023-01-01")
    private LocalDate startDate;

    @Schema(description = "End date of the contract", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Salary", example = "50000.0")
    private Double salary;

    @Schema(description = "Type of contract", example = "FULL_TIME")
    private ContractType contractType;

    @Schema(description = "Candidate ID", example = "1")
    private Integer candidateId;

    @Schema(description = "Candidate ID", example = "2")
    private Integer clientId;
}
