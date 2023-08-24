package crm.myhrcrmproject.dto.candidatesDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatesResponseDTO {
    private Integer candidateId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private VacanciesShortResponseDTO vacancyResponseDTO;
    private CandidateStatus status;
}
