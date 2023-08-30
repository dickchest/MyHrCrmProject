package crm.myhrcrmproject.dto.contractDTO;

import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponseDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String contractType;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
    private ClientResponseDTO client;
}
