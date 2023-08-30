package crm.myhrcrmproject.dto.taskDTO;

import crm.myhrcrmproject.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskStatus status;
    private Integer employeeId;
    private Integer candidateId;
    private Integer vacancyId;
}