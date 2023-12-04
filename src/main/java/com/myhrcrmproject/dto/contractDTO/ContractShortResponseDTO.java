package com.myhrcrmproject.dto.contractDTO;

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
@Schema(description = "DTO for contract details short response")
public class ContractShortResponseDTO {

    @Schema(description = "Contract ID", example = "1")
    private Integer id;

    @Schema(description = "Start date of the contract", example = "2023-01-01")
    private LocalDate startDate;

    @Schema(description = "End date of the contract", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Salary", example = "50000.0")
    private Double salary;
}
