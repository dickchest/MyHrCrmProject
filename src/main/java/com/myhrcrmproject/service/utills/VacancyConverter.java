package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VacancyConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;

    public VacancyResponseDTO toDTO(Vacancy entity) {
        return VacancyResponseDTO.builder()
                .id(entity.getId())
                .jobTitle(entity.getJobTitle())
                .description(entity.getDescription())
                .salary(entity.getSalary())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .responsibleEmployee(
                        entity.getEmployee() != null ?
                                employeeConverter.toShortDTO(entity.getEmployee()) :
                                null
                )
                .candidatesList(
                        entity.getCandidates() != null ?
                                entity.getCandidates()
                                        .stream()
                                        .map(candidateConverter::toShortDTO)
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

    public Vacancy fromDTO(Vacancy entity, VacancyRequestDTO request) {
        Optional.ofNullable(request.getJobTitle()).ifPresent(entity::setJobTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(request.getSalary()).ifPresent(entity::setSalary);
        Optional.ofNullable(request.getStartDate()).ifPresent(entity::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(entity::setEndDate);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setStatus);
        Helper.setEntityById(
                request::getResponsibleEmployeeId, entity::setEmployee, employeeRepository, "Employee");
        return entity;
    }

    public Vacancy newEntity() {
        return new Vacancy();
    }

    public VacancyShortResponseDTO toShortDTO(Vacancy entity) {
        return VacancyShortResponseDTO.builder()
                .id(entity.getId())
                .jobTitle(entity.getJobTitle())
                .salary(entity.getSalary())
                .build();
    }
}
