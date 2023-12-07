package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.service.utills.EmployeeConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import com.myhrcrmproject.service.auth.SecurityHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for employee entities.
 *
 * <p>This service provides methods to perform operations on employee entities, such as retrieving,
 * creating, updating, and deleting employee records.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class EmployeeService implements CommonService<EmployeeRequestDTO, EmployeeResponseDTO> {
    private final EmployeeRepository repository;
    private final EmployeeConverter converter;
    private final SecurityHelper securityHelper;

    /**
     * Retrieves a list of all employee records.
     *
     * @return A list of response DTOs representing all employee records.
     */
    @Override
    public List<EmployeeResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an employee record by its unique identifier.
     *
     * @param id The identifier of the employee record to retrieve.
     * @return The response DTO representing the retrieved employee record.
     * @throws NotFoundException if the employee record with the specified id is not found.
     */
    @Override
    public EmployeeResponseDTO findById(Integer id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id " + id + " not found!"));

        return converter.toDTO(entity);
    }

    /**
     * Creates a new employee record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new employee record.
     * @return The response DTO representing the newly created employee record.
     */
    @Override
    public EmployeeResponseDTO create(EmployeeRequestDTO requestDTO) {
        Employee entity = converter.fromDTO(converter.newEntity(), requestDTO);

        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing employee record with new data.
     *
     * @param id         The identifier of the employee record to update.
     * @param requestDTO The request DTO containing updated data for the employee record.
     * @return The response DTO representing the updated employee record.
     * @throws NotFoundException if the employee record with the specified id is not found.
     */
    @Override
    public EmployeeResponseDTO update(Integer id, EmployeeRequestDTO requestDTO) {
        Employee existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id: " + id + " not found!"));

        // check if user has access to this entity
        if (!securityHelper.isAuthUserEqualsEmployee(existingEntity)) {
            throw new NotAcceptableStatusException("You have not permission to access this entity");
        }

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes an employee record by its unique identifier.
     *
     * @param id The identifier of the employee record to delete.
     * @throws NotFoundException if the employee record with the specified id is not found.
     */
    @Override
    public void delete(Integer id) {
        Employee entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee with id: " + id + " not found!"));
        repository.delete(entity);
    }
}
