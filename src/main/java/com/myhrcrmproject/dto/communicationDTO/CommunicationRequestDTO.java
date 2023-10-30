package com.myhrcrmproject.dto.communicationDTO;

import com.myhrcrmproject.domain.enums.CommunicationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunicationRequestDTO {
    private LocalDateTime communicationDateTime;
    private CommunicationType communicationType;
    private Integer clientId;
    private Integer candidateId;
    private Integer vacancyId;
    private Integer employeeId;
}
