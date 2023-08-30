package crm.myhrcrmproject.dto.communicationDTO;

import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.clientContactDTO.ClientContactsShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationRequestDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private ClientContactsShortResponseDTO contact;
    private CandidateShortResponseDTO candidate;
    private VacancyShortResponseDTO vacancy;
    private EmployeeShortResponseDTO employee;
}
