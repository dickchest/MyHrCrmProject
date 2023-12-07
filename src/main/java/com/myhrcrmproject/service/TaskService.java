package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.TaskStatus;
import com.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.TaskRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.utills.TaskConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for task entities.
 *
 * <p>This service provides methods to perform operations on task entities, such as retrieving,
 * creating, updating, and deleting task records. It also includes additional methods for finding
 * task records based on different criteria.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class TaskService implements CommonService<TaskRequestDTO, TaskResponseDTO> {
    private final TaskRepository repository;
    private final TaskConverter converter;
    private final EmployeeRepository employeeRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final SecurityHelper securityHelper;

    /**
     * Retrieves a list of all task records.
     *
     * @return A list of response DTOs representing all task records.
     */
    public List<TaskResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a task record by its unique identifier.
     *
     * @param id The identifier of the task record to retrieve.
     * @return The response DTO representing the retrieved task record.
     * @throws NotFoundException if the task record with the specified id is not found.
     */
    public TaskResponseDTO findById(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return converter.toDTO(entity);
    }

    /**
     * Creates a new task record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new task record.
     * @return The response DTO representing the newly created task record.
     */
    public TaskResponseDTO create(TaskRequestDTO requestDTO) {

        Task entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set current auth user as employee
        if (entity.getEmployee() == null) {
            Optional<Employee> employee = securityHelper.getCurrentAuthEmployee();
            employee.ifPresent(entity::setEmployee);
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing task record with new data.
     *
     * @param id         The identifier of the task record to update.
     * @param requestDTO The request DTO containing updated data for the task record.
     * @return The response DTO representing the updated task record.
     * @throws NotFoundException if the task record with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public TaskResponseDTO update(Integer id, TaskRequestDTO requestDTO) {
        Task existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(existingEntity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // ....
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a task record by its unique identifier.
     *
     * @param id The identifier of the task record to delete.
     * @throws NotFoundException if the task record with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public void delete(Integer id) {
        Task entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }
        repository.delete(entity);
    }

    /**
     * Retrieves a list of task records based on the task status.
     *
     * @param id The identifier of the task status.
     * @return A list of response DTOs representing task records with the specified status.
     */
    public List<TaskResponseDTO> findAllByTaskStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                TaskStatus.class,
                repository::findAllByStatus,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of task records associated with a specific employee.
     *
     * @param id The identifier of the employee to filter by.
     * @return A list of response DTOs representing task records associated with the specified employee.
     * @throws NotFoundException if the employee with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<TaskResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return repository.findAllByEmployee(employee).stream()
                .map(converter::toDTO)
                .toList();
    }

    /**
     * Retrieves a list of task records associated with a specific candidate.
     *
     * @param id The identifier of the candidate to filter by.
     * @return A list of response DTOs representing task records associated with the specified candidate.
     */
    public List<TaskResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id, candidateRepository,
                repository::findAllByCandidate,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of task records associated with a specific vacancy.
     *
     * @param id The identifier of the vacancy to filter by.
     * @return A list of response DTOs representing task records associated with the specified vacancy.
     */
    public List<TaskResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findAllByVacancy,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of task records based on the start date.
     *
     * @param requestDTO The request DTO containing the start date.
     * @return A list of response DTOs representing task records with the specified start date.
     */
    public List<TaskResponseDTO> findAllByStartDate(TaskDateRequestDTO requestDTO) {
        LocalDate date = requestDTO.getDate();
        List<Task> list = repository.findAllByStartDate(date);
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    /**
     * Retrieves a list of short task records based on the date and employee id.
     *
     * @param id The identifier of the employee to filter by.
     * @param requestDTO The request DTO containing the date.
     * @return A list of short response DTOs representing task records with the specified date and associated with the employee.
     * @throws NotFoundException if the employee with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<TaskShortResponseDTO> findAllByDateAndEmployeeId(Integer id, TaskDateRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }
        LocalDate date = requestDTO.getDate();
        List<Task> list = repository.findAllByStartDateAndEmployee(date, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    /**
     * Retrieves a list of short task records based on the candidate id and employee id.
     *
     * @param candidateId The identifier of the candidate to filter by.
     * @param employeeId The identifier of the employee to filter by.
     * @return A list of short response DTOs representing task records associated with the specified candidate and employee.
     * @throws NotFoundException if the employee or candidate with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<TaskShortResponseDTO> findAllByCandidateIdAndEmployeeId(Integer candidateId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidate with id " + candidateId + " not found!"));
        List<Task> list = repository.findAllByCandidateAndEmployee(candidate, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    /**
     * Retrieves a list of short task records based on the status id and employee id.
     *
     * @param statusId The identifier of the task status.
     * @param employeeId The identifier of the employee to filter by.
     * @return A list of short response DTOs representing task records with the specified status and associated with the employee.
     * @throws NotFoundException if the employee with the specified id is not found or if no enum is found with the given id.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<TaskShortResponseDTO> findAllByStatusIdAndEmployeeId(Integer statusId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }
        TaskStatus status;

        try {
            status = TaskStatus.values()[statusId];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NotFoundException("No enum found with id: " + statusId);
        }

        List<Task> list = repository.findAllByStatusAndEmployee(status, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    /**
     * Retrieves a list of short task records based on the vacancy id and employee id.
     *
     * @param vacancyId The identifier of the vacancy to filter by.
     * @param employeeId The identifier of the employee to filter by.
     * @return A list of short response DTOs representing task records associated with the specified vacancy and employee.
     * @throws NotFoundException if the employee or vacancy with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<TaskShortResponseDTO> findAllByVacancyIdAndEmployeeId(Integer vacancyId, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + vacancyId + " not found!"));
        List<Task> list = repository.findAllByVacancyAndEmployee(vacancy, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }
}
