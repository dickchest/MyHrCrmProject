package crm.myhrcrmproject.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesForVacanciesResponseDTO {
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String position;
}
