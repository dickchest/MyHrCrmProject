package crm.myhrcrmproject.dto.communicationsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationsResponseDTO {
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private Integer contact_id;
    private Integer candidateId;
    private Integer vacancyId;
}