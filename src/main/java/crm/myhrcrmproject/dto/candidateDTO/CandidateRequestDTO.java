package crm.myhrcrmproject.dto.candidateDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import crm.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
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
    private ContactDetailsDTO contactDetails;
    private AddressDetailsDTO addressDetails;
    private Integer vacancyId;
    private CandidateStatus status;
}
