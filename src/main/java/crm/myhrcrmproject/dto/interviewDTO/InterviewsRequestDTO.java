package crm.myhrcrmproject.dto.interviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewsRequestDTO {
    private LocalDateTime dateTime;
    private String location;
    private String comments;
    private Integer candidateId;
    private Integer employeeId;
}