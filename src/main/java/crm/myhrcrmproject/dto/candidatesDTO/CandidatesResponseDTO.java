package crm.myhrcrmproject.dto.candidatesDTO;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.interviewsDTO.InterviewsShortResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesShortResponseDTO;
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
public class CandidatesResponseDTO{
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private String address;
    private VacanciesShortResponseDTO vacancy;
    private CandidateStatus status;
    private List<InterviewsShortResponseDTO> interviewsList;
}
