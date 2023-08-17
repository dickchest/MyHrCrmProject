package crm.myhrcrmproject.dto.contractsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesForVacanciesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractsRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String contractType;
    private Integer candidateId;
    private Integer clientId;
}
