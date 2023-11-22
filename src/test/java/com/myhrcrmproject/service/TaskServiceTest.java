package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.domain.enums.TaskStatus;
import com.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.TaskRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.utills.TaskConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository repository;
    @Mock
    private TaskConverter converter;
    @Mock
    private SecurityHelper securityHelper;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private VacancyRepository vacancyRepository;
    @Mock
    private TaskStatus taskStatus;
    @InjectMocks
    private TaskService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_success() {
        // Arrange
        TaskRequestDTO requestDTO = new TaskRequestDTO();
        requestDTO.setTitle("Test Title");

        Task entity = new Task();
        entity.setId(1);
        entity.setTitle(requestDTO.getTitle());

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(entity.getId());
        requestDTO.setTitle(entity.getTitle());

        // Mock repository to return an empty optional when findById is called
        when(converter.newEntity()).thenReturn(new Task());
        when(converter.fromDTO(any(Task.class), eq(requestDTO))).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(converter.toDTO(entity)).thenReturn(responseDTO);
        when(securityHelper.getCurrentAuthEmployee()).thenReturn(Optional.of(new Employee()));

        // when: perform the actual test
        TaskResponseDTO result = service.create(requestDTO);

        // then
        verify(repository).save(entity);
        verify(repository, times(1)).save(any(Task.class));
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
        assertEquals("Task with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testUpdate_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(repository.findById(id)).thenReturn(Optional.of(new Task()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class, () -> service.update(id, new TaskRequestDTO()));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.findAllByEmployeeId(id));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
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
                () -> service.findAllByDateAndEmployeeId(id, new TaskDateRequestDTO()));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
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
                () -> service.findAllByDateAndEmployeeId(id, new TaskDateRequestDTO()));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByCandidateIdAndEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByCandidateIdAndEmployeeId(1, id));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByCandidateIdAndEmployeeId_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class,
                () -> service.findAllByCandidateIdAndEmployeeId(1, id));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByCandidateIdAndEmployeeId_throwNotAcceptableStatusException_wrongCandidate() {
        // Arrange
        int id = 1;
        int candidateId = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(true);
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByCandidateIdAndEmployeeId(candidateId, id));
        assertEquals("Candidate with id " + candidateId + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByStatusIdAndEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByStatusIdAndEmployeeId(1, id));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByStatusIdAndEmployeeId_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class,
                () -> service.findAllByStatusIdAndEmployeeId(1, id));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByStatusIdAndEmployeeId_throwNotAcceptableStatusException_wrongCandidate() {
        // Arrange
        int id = 1;
        int statusId = 100;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(true);

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByStatusIdAndEmployeeId(statusId, id));
        assertEquals("No enum found with id: " + statusId, exception.getMessage());
    }

    @Test
    void testFindAllByVacancyIdAndEmployeeId_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByVacancyIdAndEmployeeId(1, id));
        assertEquals("Employee with id " + id + " not found!", exception.getMessage());
    }

    @Test
    void testFindAllByVacancyIdAndEmployeeId_throwNotAcceptableStatusException() {
        // Arrange
        int id = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(false);

        // Act and assert
        Exception exception = assertThrows(NotAcceptableStatusException.class,
                () -> service.findAllByVacancyIdAndEmployeeId(1, id));
        assertEquals("406 NOT_ACCEPTABLE \"You have not permission to access this entity\"", exception.getMessage());
    }

    @Test
    void testFindAllByVacancyIdAndEmployeeId_throwNotAcceptableStatusException_wrongVacancy() {
        // Arrange
        int id = 1;
        int vacancyId = 1;

        // Mock reposting to return an empty optional when findById is called
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        when(securityHelper.isAuthUserEqualsEmployee(any(Employee.class))).thenReturn(true);
        when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> service.findAllByVacancyIdAndEmployeeId(vacancyId, id));
        assertEquals("Vacancy with id " + vacancyId + " not found!", exception.getMessage());
    }
}