package com.myhrcrmproject.dto.vacancyDTO;

import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO for vacancy response")
public class VacancyResponseDTO {

    @Schema(description = "Vacancy ID", example = "1")
    private Integer id;

    @Schema(description = "Vacancy job title", example = "Software Engineer")
    private String jobTitle;

    @Schema(description = "Vacancy description", example = "Java Developer Position")
    private String description;

    @Schema(description = "Salary", example = "50000.0")
    private Double salary;

    @Schema(description = "Start date of the vacancy", example = "2023-10-25")
    private LocalDate startDate;

    @Schema(description = "End date of the vacancy", example = "2023-12-31")
    private LocalDate endDate;

    @Schema(description = "Status of the vacancy", example = "OPEN")
    private VacancyStatus status;

    @Schema(description = "Details of the responsible employee")
    private EmployeeShortResponseDTO responsibleEmployee;

    @Schema(description = "List of applied candidates")
    private List<CandidateShortResponseDTO> candidatesList;
}
