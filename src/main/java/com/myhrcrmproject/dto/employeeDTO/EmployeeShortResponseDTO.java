package com.myhrcrmproject.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeShortResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
}
