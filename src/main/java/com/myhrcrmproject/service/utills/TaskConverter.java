package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.VacancyRepository;
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
        Helper.setEntityById(
                request::getCandidateId, entity::setCandidate, candidateRepository, "Candidate");
        Helper.setEntityById(
                request::getVacancyId, entity::setVacancy, vacancyRepository, "Vacancy");
        Helper.setEntityById(
                request::getEmployeeId, entity::setEmployee, employeeRepository, "Employee");
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
