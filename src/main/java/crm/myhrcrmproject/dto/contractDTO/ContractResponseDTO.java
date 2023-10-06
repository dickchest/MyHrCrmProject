package crm.myhrcrmproject.dto.contractDTO;

import crm.myhrcrmproject.domain.enums.ContractType;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
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
