package crm.myhrcrmproject.dto.contractsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractsResponseDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String contractType;
    private CandidatesShortResponseDTO candidate;
    private EmployeesShortResponseDTO employee;
    private ClientsResponseDTO client;
}
