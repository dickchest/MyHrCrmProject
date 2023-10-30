package com.myhrcrmproject.dto.vacancyDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyShortResponseDTO {
    private Integer id;
    private String jobTitle;
    private Double salary;
}
