package crm.myhrcrmproject.dto.employeeDTO;

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
    private String email;
    private String phone;
}
