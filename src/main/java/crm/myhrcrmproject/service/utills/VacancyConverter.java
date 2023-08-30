package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacancyConverter {
    private final CandidateConverter candidateConverter;
    private final EmployeeRepository employeeRepository;

    public VacancyConverter(CandidateConverter candidateConverter, EmployeeRepository employeeRepository) {
        this.candidateConverter = candidateConverter;
        this.employeeRepository = employeeRepository;
    }


    public VacancyResponseDTO toDTO(Vacancy entity) {
        return VacancyResponseDTO.builder()
                .id(entity.getId())
                .jobTitle(entity.getJobTitle())
                .description(entity.getDescription())
                .salary(entity.getSalary())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .responsibleEmployee(new EmployeeShortResponseDTO()) // todo поменять на метод
                .candidatesList(
                        entity.getCandidate() != null ?
                                entity.getCandidate()
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
        Optional.ofNullable(request.getResponsibleEmployeeId()).ifPresent(
                id -> entity.setEmployee(employeeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Employee with id " + request.getResponsibleEmployeeId() + " not found"))));
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
