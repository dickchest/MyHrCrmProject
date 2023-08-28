package crm.myhrcrmproject.dto.vacanciesDTO;

import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacanciesResponseDTO {
    private Integer id;
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacancyStatus status;
    private EmployeesShortResponseDTO responsibleEmployee;
    private List<CandidatesShortResponseDTO> candidatesList;
}
