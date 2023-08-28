package crm.myhrcrmproject.dto.candidatesDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidatesShortResponseDTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private CandidateStatus status;
}
