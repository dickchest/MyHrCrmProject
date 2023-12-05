package com.myhrcrmproject.dto.userDetailsDTO;

import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User details DTO for response")
public class UserDetailsResponseDTO {

    @Schema(description = "User details ID", example = "1")
    private Integer id;

    @Schema(description = "User name", example = "TestUser1")
    private String userName;

    @Schema(description = "User role name", example = "manager")
    private String roleName;

    @Schema(description = "Details of the associated employee")
    private EmployeeShortResponseDTO employee;

    @Schema(description = "Date of creation", example = "2023-11-12T17:25:06")
    private LocalDateTime createdDate;

    @Schema(description = "Date of last update", example = "2023-11-26T13:30:00")
    private LocalDateTime updatedDate;
}
