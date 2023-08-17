package crm.myhrcrmproject.dto.communicationsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.clientContactsDTO.ClientContactsShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesForVacanciesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesForCandidatesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationsResponseDTO {
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private Integer contact_id;
    private Integer candidate_id;
    private Integer vacancy_id;
}
