package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.*;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationShortResponseDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeShortResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunicationConverterTest {
    @Mock
    private CandidateConverter candidateConverter;
    @Mock
    private EmployeeConverter employeeConverter;
    @Mock
    private ClientConverter clientConverter;
    @InjectMocks
    private CommunicationConverter converter;

    @Test
    void testToShortDTO_CandidateEmployeeClient() {
        Candidate candidate = new Candidate();
        candidate.setId(1);

        Employee employee = new Employee();
        employee.setId(1);

        Client client = new Client();
        client.setId(1);

        Communication entity = new Communication();
        entity.setId(1);
        entity.setCandidate(candidate);
        entity.setEmployee(employee);
        entity.setClient(client);

        CandidateShortResponseDTO candidateShortResponseDTO = new CandidateShortResponseDTO();
        candidateShortResponseDTO.setId(1);

        EmployeeShortResponseDTO employeeShortResponseDTO = new EmployeeShortResponseDTO();
        employeeShortResponseDTO.setId(1);

        ClientShortResponseDTO clientShortResponseDTO = new ClientShortResponseDTO();
        clientShortResponseDTO.setId(1);

        // Mock
        when(candidateConverter.toShortDTO(any())).thenReturn(candidateShortResponseDTO);
        when(employeeConverter.toShortDTO(any())).thenReturn(employeeShortResponseDTO);
        when(clientConverter.toShortDTO(any())).thenReturn(clientShortResponseDTO);

        CommunicationShortResponseDTO response = converter.toShortDTO(entity);

        assertNotNull(response);
    }

    @Test
    void testToShortDTO_noCandidateEmployeeClient() {
        Communication entity = new Communication();
        entity.setId(1);

        CommunicationShortResponseDTO response = converter.toShortDTO(entity);

        assertNotNull(response);
    }
}