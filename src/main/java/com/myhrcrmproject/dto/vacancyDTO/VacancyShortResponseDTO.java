package com.myhrcrmproject.dto.vacancyDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for vacancy short response")
public class VacancyShortResponseDTO {

    @Schema(description = "Vacancy ID", example = "1")
    private Integer id;

    @Schema(description = "Vacancy job title", example = "Software Engineer")
    private String jobTitle;

    @Schema(description = "Salary", example = "50000.0")
    private Double salary;
}
