package crm.myhrcrmproject.dto.tasksDTO;

import crm.myhrcrmproject.domain.enums.TasksStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesForVacanciesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesForCandidatesResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TasksStatus status;
    private Integer employeeId;
    private Integer candidateId;
    private Integer vacancyId;
}