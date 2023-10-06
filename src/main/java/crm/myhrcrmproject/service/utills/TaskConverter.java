package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Task;
import crm.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import crm.myhrcrmproject.repository.CandidateRepository;
import crm.myhrcrmproject.repository.ClientRepository;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskConverter {
    private final CandidateConverter candidateConverter;
    private final CandidateRepository candidateRepository;
    private final EmployeeConverter employeeConverter;
    private final EmployeeRepository employeeRepository;
    private final VacancyConverter vacancyConverter;
    private final VacancyRepository vacancyRepository;

    public TaskResponseDTO toDTO(Task entity) {
        return TaskResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .candidate(entity.getCandidate() != null ?
                        candidateConverter.toShortDTO(entity.getCandidate()) : null)
                .employee(entity.getEmployee() != null ?
                        employeeConverter.toShortDTO(entity.getEmployee()) : null)
                .vacancy(entity.getVacancy() != null ?
                        vacancyConverter.toShortDTO(entity.getVacancy()) : null)
                .build();
    }

    public Task fromDTO(Task entity, TaskRequestDTO request) {
        Optional.ofNullable(request.getTitle()).ifPresent(entity::setTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(request.getStartDate()).ifPresent(entity::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(entity::setEndDate);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);

        Optional.ofNullable(request.getCandidateId()).ifPresent(
                id -> entity.setCandidate(Helper.findByIdOrThrow(
                        candidateRepository, id, "Candidate")));
        Optional.ofNullable(request.getVacancyId()).ifPresent(
                id -> entity.setVacancy(Helper.findByIdOrThrow(
                        vacancyRepository, id, "Vacancy")));
        Optional.ofNullable(request.getEmployeeId()).ifPresent(
                id -> entity.setEmployee(Helper.findByIdOrThrow(
                        employeeRepository, id, "Employee")));
        return entity;
    }

    public Task newEntity() {
        return new Task();
    }

    public TaskShortResponseDTO toShortDTO(Task entity) {
        return TaskShortResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .build();
    }
}
