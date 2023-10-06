package crm.myhrcrmproject.dto.taskDTO;

import crm.myhrcrmproject.domain.enums.TaskStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskShortResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
}