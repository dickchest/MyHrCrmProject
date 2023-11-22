package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.InterviewRepository;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.utills.InterviewConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InterviewServiceTest {
    @Mock
    InterviewRepository repository;

    @Mock
    InterviewConverter converter;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    SecurityHelper securityHelper;

    @InjectMocks
    InterviewService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_success() {
        // Arrange
        int id = 1;
        InterviewRequestDTO requestDTO = new InterviewRequestDTO();
        requestDTO.setDate(LocalDate.of(2013, 10, 15));
        requestDTO.setTime(LocalTime.of(12, 0));
        requestDTO.setLocation("Test Location");
        requestDTO.setCandidateId(1);

        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setFirstName("Test Name");
        candidate.setLastName("Test Lastname");

        Interview entity = new Interview();
        entity.setId(1);
        entity.setDate(requestDTO.getDate());
        entity.setTime(requestDTO.getTime());
        entity.setLocation(requestDTO.getLocation());
        entity.setCandidate(candidate);

        InterviewResponseDTO responseDTO = new InterviewResponseDTO();
        responseDTO.setId(1);
        responseDTO.setLocation(entity.getLocation());

        // Mock repository to return an empty optional when findById is called
        when(converter.newEntity()).thenReturn(new Interview());
        when(converter.fromDTO(any(Interview.class), eq(requestDTO))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(converter.toDTO(entity)).thenReturn(responseDTO);

        // when: perform the actual test
        InterviewResponseDTO result = service.create(requestDTO);

        // then
        verify(repository).save(entity);
        verify(repository, times(1)).save(any(Interview.class));
        assertEquals(responseDTO, result);
    }

    @Test
    void testDelete_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.delete(id));
        assertEquals("Interview with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.findAllByEmployeeId(id));
        assertEquals("Entity with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByEmployeeId_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class, () -> service.findAllByEmployeeId(id));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByDateAndEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByDateAndEmployeeId(new InterviewDateRequestDTO(), id));
        assertEquals("Entity with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByDateAndEmployeeId_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class,
                () -> service.findAllByDateAndEmployeeId(new InterviewDateRequestDTO(), id));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

}