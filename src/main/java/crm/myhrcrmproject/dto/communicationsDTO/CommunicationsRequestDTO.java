package crm.myhrcrmproject.dto.communicationsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.clientContactsDTO.ClientContactsShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationsRequestDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private ClientContactsShortResponseDTO contact;
    private CandidatesShortResponseDTO candidate;
    private VacanciesShortResponseDTO vacancy;
    private EmployeesShortResponseDTO employee;
}
