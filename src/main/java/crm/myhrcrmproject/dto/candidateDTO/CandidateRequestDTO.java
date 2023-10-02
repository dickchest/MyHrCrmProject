package crm.myhrcrmproject.dto.candidateDTO;

import crm.myhrcrmproject.domain.AddressDetails;
import crm.myhrcrmproject.domain.ContactDetails;
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
    private ContactDetails contactDetails;
    private AddressDetails addressDetails;
    private Integer vacancyId;
    private CandidateStatus status;
}
