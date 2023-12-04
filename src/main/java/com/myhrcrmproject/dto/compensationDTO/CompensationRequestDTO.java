package com.myhrcrmproject.dto.compensationDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for compensation request")
public class CompensationRequestDTO {

    @Schema(description = "Salary amount", example = "50000.0")
    private Double salary;

    @Schema(description = "Date of payment", example = "2023-08-01")
    private LocalDate paymentDate;

    @Schema(description = "Additional comments", example = "Performance bonus")
    private String comments;

    @Schema(description = "Candidate ID", example = "1")
    private Integer candidateId;

    @Schema(description = "Contract ID", example = "1")
    private Integer contractId;
}
