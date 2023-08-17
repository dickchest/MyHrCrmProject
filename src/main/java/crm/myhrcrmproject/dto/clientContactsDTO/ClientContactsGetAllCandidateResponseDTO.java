package crm.myhrcrmproject.dto.clientContactsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesForInterviewsResponseDTO;
import crm.myhrcrmproject.dto.clientsDTO.ClientsResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsGetAllCandidateResponseDTO {
    private Integer ClientContactId;
    private List<CandidatesForInterviewsResponseDTO> candidatesList;
}
