package com.myhrcrmproject.dto.vacancyDTO;

import com.myhrcrmproject.domain.enums.VacancyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for vacancy request")
public class VacancyRequestDTO {

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

    @Schema(description = "ID of the responsible employee")
    private Integer responsibleEmployeeId;
}
