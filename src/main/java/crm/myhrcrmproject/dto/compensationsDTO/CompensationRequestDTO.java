package crm.myhrcrmproject.dto.compensationsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.contractsDTO.ContractsShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompensationRequestDTO {
    private Double salary;
    private LocalDate paymentDate;
    private String comments;
    private Integer candidateId;
    private Integer contractId;
}
