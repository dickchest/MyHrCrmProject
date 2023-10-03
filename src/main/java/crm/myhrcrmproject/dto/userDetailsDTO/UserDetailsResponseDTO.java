package crm.myhrcrmproject.dto.userDetailsDTO;

import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsResponseDTO {
    private Integer id;
    private String userName;
    private String roleName;
    private EmployeeShortResponseDTO employee;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
