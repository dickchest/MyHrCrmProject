package crm.myhrcrmproject.dto.vacancyDTO;

import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
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
public class VacancyResponseDTO {
    private Integer id;
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacancyStatus status;
    private EmployeeShortResponseDTO responsibleEmployee;
    private List<CandidateShortResponseDTO> candidatesList;
}
