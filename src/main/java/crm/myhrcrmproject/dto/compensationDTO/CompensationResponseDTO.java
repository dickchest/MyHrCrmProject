package crm.myhrcrmproject.dto.compensationDTO;

import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationResponseDTO {
    private Integer id;
    private Double salary;
    private LocalDate paymentDate;
    private String comments;
    private CandidateShortResponseDTO candidate;
    private ContractShortResponseDTO contract;
}
