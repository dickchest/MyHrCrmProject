package crm.myhrcrmproject.dto.tasksDTO;

import crm.myhrcrmproject.domain.enums.TasksStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesForVacanciesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesForCandidatesResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private Integer taskId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TasksStatus status;
    private EmployeesForVacanciesResponseDTO employee;
    private CandidatesForInterviewsResponseDTO candidate;
    private VacanciesForCandidatesResponseDTO vacancy;
}