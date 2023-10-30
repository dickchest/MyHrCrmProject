package com.myhrcrmproject.dto.vacancyDTO;

import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyResponseDTO {
    private Integer id;
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacancyStatus status;
    private EmployeeShortResponseDTO responsibleEmployee;
    private List<CandidateShortResponseDTO> candidatesList;
}
