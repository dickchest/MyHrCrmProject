package crm.myhrcrmproject.dto.employeeDTO;

import crm.myhrcrmproject.domain.ContactDetails;
import crm.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {
    private String firstName;
    private String lastName;
    private String position;
    private ContactDetailsDTO contactDetails;
}
