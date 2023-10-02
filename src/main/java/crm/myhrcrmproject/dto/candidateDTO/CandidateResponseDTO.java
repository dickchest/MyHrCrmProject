package crm.myhrcrmproject.dto.candidateDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import crm.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import crm.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponseDTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private ContactDetailsDTO contactDetails;
    private AddressDetailsDTO addressDetails;
    private VacancyShortResponseDTO vacancy;
    private CandidateStatus status;
    private List<InterviewShortResponseDTO> interviewsList;
}
