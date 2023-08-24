package crm.myhrcrmproject.dto.tasksDTO;

import crm.myhrcrmproject.domain.enums.TaskStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private Integer taskId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
    private EmployeesShortResponseDTO employee;
    private CandidatesShortResponseDTO candidate;
    private VacanciesShortResponseDTO vacancy;
}