package crm.myhrcrmproject.dto.compensationsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.contractsDTO.ContractsShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationResponseDTO {
    private Integer compensationId;
    private Double salary;
    private LocalDate paymentDate;
    private String comments;
    private CandidatesShortResponseDTO candidate;
    private ContractsShortResponseDTO contract;
}