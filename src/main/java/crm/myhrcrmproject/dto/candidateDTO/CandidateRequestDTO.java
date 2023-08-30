package crm.myhrcrmproject.dto.candidateDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequestDTO{

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private Integer vacancyId;
    private CandidateStatus status;
}
