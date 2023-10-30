package com.myhrcrmproject.dto.candidateDTO;

import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import com.myhrcrmproject.domain.enums.CandidateStatus;
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
