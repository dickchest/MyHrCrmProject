package crm.myhrcrmproject.dto.contractsDTO;

import crm.myhrcrmproject.domain.Candidates;
import crm.myhrcrmproject.domain.Clients;
import crm.myhrcrmproject.domain.Employees;
import crm.myhrcrmproject.domain.Vacancies;
import crm.myhrcrmproject.domain.enums.TasksStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesForVacanciesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesForCandidatesResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractsResponseDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double salary;
    private String contractType;
    private CandidatesForInterviewsResponseDTO candidate;
    private EmployeesForVacanciesResponseDTO employee;
    private ClientsResponseDTO client;
}
