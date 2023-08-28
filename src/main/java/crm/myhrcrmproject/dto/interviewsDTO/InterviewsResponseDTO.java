package crm.myhrcrmproject.dto.interviewsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewsResponseDTO {
    private Integer id;
    private LocalDateTime dateTime;
    private String location;
    private String comments;
    private CandidatesShortResponseDTO candidate;
    private EmployeesShortResponseDTO employee;
}
