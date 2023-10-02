package crm.myhrcrmproject.dto.interviewDTO;

import crm.myhrcrmproject.domain.enums.InterviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewRequestDTO {
    private LocalDate date;
    private LocalTime time;
    private String location;
    private String comments;
    private Integer candidateId;
    private Integer employeeId;
    private InterviewStatus status;
}
