package com.myhrcrmproject.dto.vacancyDTO;

import com.myhrcrmproject.domain.enums.VacancyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRequestDTO {
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;
    private VacancyStatus status;
    private Integer responsibleEmployeeId;
}
