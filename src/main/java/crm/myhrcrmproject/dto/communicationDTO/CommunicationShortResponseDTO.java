package crm.myhrcrmproject.dto.communicationDTO;

import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationShortResponseDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private String communicationType;
    private ClientShortResponseDTO client;
    private CandidateShortResponseDTO candidate;
    private EmployeeShortResponseDTO employee;
}
