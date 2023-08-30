package crm.myhrcrmproject.dto.clientContactDTO;

import crm.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientContactsGetAllCandidateResponseDTO {
    private Integer id;
    private List<CandidateShortResponseDTO> candidatesList;
}
