package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesRequestDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.service.validation.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacanciesConverter implements Converter<Vacancy, VacanciesRequestDTO, VacanciesResponseDTO> {
    private final CandidatesConverter candidatesConverter;
    private final EmployeeRepository employeeRepository;

    public VacanciesConverter(CandidatesConverter candidatesConverter, EmployeeRepository employeeRepository) {
        this.candidatesConverter = candidatesConverter;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public VacanciesResponseDTO toDTO(Vacancy entity) {
        return VacanciesResponseDTO.builder()
                .id(entity.getId())
                .jobTitle(entity.getJobTitle())
                .description(entity.getDescription())
                .salary(entity.getSalary())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getVacancyStatus())
                .responsibleEmployee(new EmployeesShortResponseDTO()) // todo поменять на метод
                .candidatesList(
                        entity.getCandidate() != null ?
                                entity.getCandidate()
                                        .stream()
                                        .map(candidatesConverter::toShortDTO)
                                        .collect(Collectors.toList())
                                : Collections.emptyList()
                )
                .build();
    }

    @Override
    public Vacancy fromDTO(Vacancy entity, VacanciesRequestDTO request) {
        Optional.ofNullable(request.getJobTitle()).ifPresent(entity::setJobTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);
        Optional.ofNullable(request.getSalary()).ifPresent(entity::setSalary);
        Optional.ofNullable(request.getStartDate()).ifPresent(entity::setStartDate);
        Optional.ofNullable(request.getEndDate()).ifPresent(entity::setEndDate);
        Optional.ofNullable(request.getStatus()).ifPresent(entity::setVacancyStatus);
        Optional.ofNullable(request.getResponsibleEmployeeId()).ifPresent(
                id -> entity.setEmployee(employeeRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException
                                ("Employee with id " + request.getResponsibleEmployeeId() + " not found"))));
        return entity;
    }

    @Override
    public Vacancy newEntity() {
        return new Vacancy();
    }

    public VacanciesShortResponseDTO toShortDTO(Vacancy entity) {
        return VacanciesShortResponseDTO.builder()
                .id(entity.getId())
                .jobTitle(entity.getJobTitle())
                .salary(entity.getSalary())
                .build();
    }
}
