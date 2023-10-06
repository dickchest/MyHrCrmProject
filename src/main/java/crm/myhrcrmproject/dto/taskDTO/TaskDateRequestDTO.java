package crm.myhrcrmproject.dto.taskDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDateRequestDTO {
    private LocalDate date;
}
