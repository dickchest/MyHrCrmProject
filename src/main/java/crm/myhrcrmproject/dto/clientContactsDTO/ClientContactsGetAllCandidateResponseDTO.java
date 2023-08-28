package crm.myhrcrmproject.dto.clientContactsDTO;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsGetAllCandidateResponseDTO {
    private Integer id;
    private List<CandidatesShortResponseDTO> candidatesList;
}
