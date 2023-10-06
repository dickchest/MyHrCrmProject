package crm.myhrcrmproject.dto.communicationDTO;

import crm.myhrcrmproject.domain.enums.CommunicationType;
import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunicationResponseDTO {
    private Integer id;
    private LocalDateTime communicationDateTime;
    private CommunicationType communicationType;
    private ClientShortResponseDTO client;
    private CandidateShortResponseDTO candidate;
    private VacancyShortResponseDTO vacancy;
    private EmployeeShortResponseDTO employee;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
