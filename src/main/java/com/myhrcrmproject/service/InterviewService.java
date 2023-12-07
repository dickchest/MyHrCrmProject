package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Interview;
import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.InterviewRepository;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.utills.InterviewConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.InterviewStatus;
import com.myhrcrmproject.service.auth.SecurityHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for interview entities.
 *
 * <p>This service provides methods to perform operations on interview entities, such as retrieving,
 * creating, updating, and deleting interview records. It also includes additional methods for finding
 * interview records based on different criteria.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class InterviewService implements CommonService<InterviewRequestDTO, InterviewResponseDTO> {
    private final InterviewRepository repository;
    private final InterviewConverter converter;
    private final EmployeeRepository employeeRepository;
    private final SecurityHelper securityHelper;

    /**
     * Retrieves a list of all interview records.
     *
     * @return A list of response DTOs representing all interview records.
     */
    public List<InterviewResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an interview record by its unique identifier.
     *
     * @param id The identifier of the interview record to retrieve.
     * @return The response DTO representing the retrieved interview record.
     * @throws NotFoundException if the interview record with the specified id is not found.
     */
    public InterviewResponseDTO findById(Integer id) {
        Interview entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id " + id + " not found!"));
        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(entity.getEmployee())) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return converter.toDTO(entity);
    }

    /**
     * Creates a new interview record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new interview record.
     * @return The response DTO representing the newly created interview record.
     */
    public InterviewResponseDTO create(InterviewRequestDTO requestDTO) {
        Interview entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set status
        if (Optional.ofNullable(requestDTO.getStatus()).isEmpty()) {
            entity.setStatus(InterviewStatus.SCHEDULED);
        }
        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());
        repository.save(entity);
        return converter.toDTO(entity);
    }

    /**
     * Updates an existing interview record with new data.
     *
     * @param id         The identifier of the interview record to update.
     * @param requestDTO The request DTO containing updated data for the interview record.
     * @return The response DTO representing the updated interview record.
     * @throws NotFoundException if the interview record with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to update this entity.
     */
    public InterviewResponseDTO update(Integer id, InterviewRequestDTO requestDTO) {
        Interview existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes an interview record by its unique identifier.
     *
     * @param id The identifier of the interview record to delete.
     * @throws NotFoundException if the interview record with the specified id is not found.
     */
    public void delete(Integer id) {
        Interview entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Interview with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Retrieves a list of interview records based on the status.
     *
     * @param id The identifier of the interview status.
     * @return A list of response DTOs representing interview records with the specified status.
     */
    public List<InterviewShortResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                InterviewStatus.class,
                repository::findByStatus,
                converter::toShortDTO
        );
    }

    /**
     * Retrieves a list of interview records associated with a specific employee.
     *
     * @param id The identifier of the employee to filter by.
     * @return A list of response DTOs representing interview records associated with the specified employee.
     * @throws NotFoundException if the employee with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<InterviewShortResponseDTO> findAllByEmployeeId(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        return repository.findByEmployee(employee).stream()
                .map(converter::toShortDTO)
                .toList();
    }

    /**
     * Retrieves a list of interview records based on a specific date and associated with a specific employee.
     *
     * @param requestDTO The request DTO containing the date.
     * @param id         The identifier of the employee to filter by.
     * @return A list of response DTOs representing interview records with the specified date and associated with the specified employee.
     * @throws NotFoundException if the employee with the specified id is not found.
     * @throws NotAcceptableStatusException if the authenticated user does not have permission to access this entity.
     */
    public List<InterviewShortResponseDTO> findAllByDateAndEmployeeId(InterviewDateRequestDTO requestDTO, Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(employee)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        LocalDate date = requestDTO.getDate();
        List<Interview> list = repository.findByDateAndEmployee(date, employee);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }

    /**
     * Retrieves a list of interview records based on a specific date.
     *
     * @param requestDTO The request DTO containing the date.
     * @return A list of response DTOs representing interview records with the specified date.
     */
    public List<InterviewShortResponseDTO> findAllByDate(InterviewDateRequestDTO requestDTO) {
        LocalDate date = requestDTO.getDate();
        List<Interview> list = repository.findByDate(date);
        return list.stream()
                .map(converter::toShortDTO)
                .toList();
    }
}
