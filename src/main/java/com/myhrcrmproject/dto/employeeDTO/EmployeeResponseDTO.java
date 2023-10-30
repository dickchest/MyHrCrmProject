package com.myhrcrmproject.dto.employeeDTO;

import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponseDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String position;
    private ContactDetailsDTO contactDetails;
}
