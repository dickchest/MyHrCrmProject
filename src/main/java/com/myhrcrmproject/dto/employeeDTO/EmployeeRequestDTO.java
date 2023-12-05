package com.myhrcrmproject.dto.employeeDTO;

import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for employee request")
public class EmployeeRequestDTO {

    @Schema(description = "First name of the employee", example = "TestName1")
    private String firstName;

    @Schema(description = "Last name of the employee", example = "TestLastname1")
    private String lastName;

    @Schema(description = "Position of the employee", example = "position1")
    private String position;

    @Schema(description = "Contact details of the employee")
    private ContactDetailsDTO contactDetails;
}
