package com.myhrcrmproject.dto.compensationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompensationShortResponseDTO {
    private Integer id;
    private Double salary;
    private LocalDate paymentDate;
}
