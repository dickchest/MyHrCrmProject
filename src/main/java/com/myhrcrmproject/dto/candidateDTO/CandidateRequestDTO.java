package com.myhrcrmproject.dto.candidateDTO;

import com.myhrcrmproject.dto.addressDetailsDTO.AddressDetailsDTO;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.domain.enums.CandidateStatus;
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
