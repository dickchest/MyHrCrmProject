package com.myhrcrmproject.dto.compensationDTO;

import com.myhrcrmproject.dto.contractDTO.ContractShortResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
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
@Schema(description = "DTO for compensation response")
public class CompensationResponseDTO {

    @Schema(description = "Compensation ID", example = "1")
    private Integer id;

    @Schema(description = "Salary amount", example = "50000.0")
    private Double salary;

    @Schema(description = "Date of payment", example = "2023-08-01")
    private LocalDate paymentDate;

    @Schema(description = "Additional comments", example = "Performance bonus")
    private String comments;

    @Schema(description = "Candidate information")
    private CandidateShortResponseDTO candidate;

    @Schema(description = "Contract information")
    private ContractShortResponseDTO contract;
}