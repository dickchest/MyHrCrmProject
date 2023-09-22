package crm.myhrcrmproject.dto.interviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDateRequestDTO {
    private LocalDateTime dateTime;
}
