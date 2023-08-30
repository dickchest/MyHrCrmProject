package crm.myhrcrmproject.dto.interviewDTO;

import crm.myhrcrmproject.domain.enums.InterviewStatus;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewsShortResponseDTO {
    private Integer id;
    private LocalDateTime dateTime;
    private InterviewStatus status;
    private String comments;
    private EmployeeShortResponseDTO employee;
}
