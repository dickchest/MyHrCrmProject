package com.myhrcrmproject.dto.compensationDTO;

import com.myhrcrmproject.dto.contractDTO.ContractShortResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompensationResponseDTO {
    private Integer id;
    private Double salary;
    private LocalDate paymentDate;
    private String comments;
    private CandidateShortResponseDTO candidate;
    private ContractShortResponseDTO contract;
}
