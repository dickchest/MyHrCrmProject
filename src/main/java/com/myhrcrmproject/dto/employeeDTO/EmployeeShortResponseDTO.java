package com.myhrcrmproject.dto.employeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for employee response")
public class EmployeeShortResponseDTO {

    @Schema(description = "Employee ID", example = "1")
    private Integer id;

    @Schema(description = "First name of the employee", example = "TestName1")
    private String firstName;

    @Schema(description = "Last name of the employee", example = "TestLastname1")
    private String lastName;

    @Schema(description = "Position of the employee", example = "position1")
    private String position;
}
