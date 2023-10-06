package crm.myhrcrmproject.dto.communicationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationRequestDTO {
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private Integer clientId;
    private Integer candidateId;
    private Integer vacancyId;
    private Integer employeeId;
}
