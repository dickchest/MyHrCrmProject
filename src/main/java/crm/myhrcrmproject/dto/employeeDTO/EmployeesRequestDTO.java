package crm.myhrcrmproject.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesRequestDTO {
    private String firstName;
    private String lastName;
    private String position;
    private String email;
    private String phone;
}