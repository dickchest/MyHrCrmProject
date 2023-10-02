package crm.myhrcrmproject.dto.interviewDTO;

import crm.myhrcrmproject.domain.enums.InterviewStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewResponseDTO {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String comments;
    private InterviewStatus status;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
}
