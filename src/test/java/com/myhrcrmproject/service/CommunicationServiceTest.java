package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Communication;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import com.myhrcrmproject.repository.CommunicationRepository;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.utills.CommunicationConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CommunicationServiceTest {
    @Mock
    private CommunicationRepository repository;
    @Mock
    private CommunicationConverter converter;

    @Mock
    private SecurityHelper helper;

    @InjectMocks
    private CommunicationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.findById(id));
        assertEquals("Communication with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testDelete_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.delete(id));
        assertEquals("Communication with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testCreateCommunication_shouldSetCurrentDateAndTime() {
        // given
        CommunicationRequestDTO requestDTO = new CommunicationRequestDTO();
        requestDTO.setCommunicationType(CommunicationType.EMAIL);

        Employee employee = new Employee();
        employee.setLastName("TestLastname");
        employee.setId(1);

        Communication communication = new Communication();
        communication.setId(1);
        communication.setCommunicationType(CommunicationType.EMAIL);
        communication.setCommunicationDateTime(LocalDateTime.of(2023, 11, 15, 12, 0));
        communication.setEmployee(employee);

        CommunicationResponseDTO responseDTO = new CommunicationResponseDTO();
        responseDTO.setId(1);
        responseDTO.setCommunicationType(CommunicationType.EMAIL);


        // when
        when(converter.newEntity()).thenReturn(new Communication());
        when(converter.fromDTO(any(Communication.class), eq(requestDTO))).thenReturn(communication);
        when(repository.save(communication)).thenReturn(communication);
        when(converter.toDTO(communication)).thenReturn(responseDTO);
        when(helper.getCurrentAuthEmployee()).thenReturn(Optional.of(employee));

        // when: perform the actual test
        CommunicationResponseDTO result = service.create(requestDTO);

        // then
        verify(repository).save(communication);
        verify(repository, times(1)).save(any(Communication.class));
        assertEquals(responseDTO, result);
    }

}