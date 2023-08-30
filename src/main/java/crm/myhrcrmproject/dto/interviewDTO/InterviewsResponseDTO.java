package crm.myhrcrmproject.dto.interviewDTO;

import crm.myhrcrmproject.domain.enums.InterviewStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
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
    private InterviewStatus status;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
}
