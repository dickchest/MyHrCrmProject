package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.VacancyRepository;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.utills.VacancyConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.VacancyStatus;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for vacancy entities.
 *
 * <p>This service provides methods to perform operations on vacancy entities, such as retrieving,
 * creating, updating, and deleting vacancy records. It also includes extra methods for finding
 * vacancies by status or employee.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class VacancyService implements CommonService<VacancyRequestDTO, VacancyResponseDTO> {
    private final VacancyRepository repository;
    private final VacancyConverter converter;
    private final EmployeeRepository employeeRepository;

    /**
     * Retrieves a list of vacancy records.
     *
     * @return A list of response DTOs representing vacancy records.
     */
    @Override
    public List<VacancyResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a vacancy record based on the provided id.
     *
     * @param id The identifier of the vacancy record to retrieve.
     * @return The response DTO representing the vacancy record.
     * @throws NotFoundException if the vacancy record with the specified id is not found.
     */
    @Override
    public VacancyResponseDTO findById(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    /**
     * Creates a new vacancy record based on the provided request DTO.
     *
     * @param requestDTO The request DTO containing information for creating the vacancy record.
     * @return The response DTO representing the created vacancy record.
     */
    @Override
    public VacancyResponseDTO create(VacancyRequestDTO requestDTO) {
        Vacancy entity = converter.fromDTO(converter.newEntity(), requestDTO);

        entityAfterCreateProcedures(entity, requestDTO);
        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing vacancy record based on the provided id and request DTO.
     *
     * @param id The identifier of the vacancy record to update.
     * @param requestDTO The request DTO containing information for updating the vacancy record.
     * @return The response DTO representing the updated vacancy record.
     * @throws NotFoundException if the vacancy record with the specified id is not found.
     */
    @Override
    public VacancyResponseDTO update(Integer id, VacancyRequestDTO requestDTO) {
        Vacancy existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a vacancy record based on the provided id.
     *
     * @param id The identifier of the vacancy record to delete.
     * @throws NotFoundException if the vacancy record with the specified id is not found.
     */
    @Override
    public void delete(Integer id) {
        Vacancy entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacancy with id: " + id + " not found!"));
        repository.delete(entity);
    }

    // Extra method for create and update
    private void entityAfterCreateProcedures(Vacancy entity, VacancyRequestDTO requestDTO) {
        if (requestDTO.getStatus() == null) {
            entity.setStatus(VacancyStatus.OPEN);
        }
    }

    /**
     * Retrieves a list of vacancy records by status.
     *
     * @param id The identifier of the vacancy status.
     * @return A list of response DTOs representing vacancy records with the specified status.
     */
    public List<VacancyResponseDTO> findAllByStatusId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                VacancyStatus.class,
                repository::findByStatus,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of short response DTOs for vacancies associated with an employee.
     *
     * @param id The identifier of the employee.
     * @return A list of short response DTOs representing vacancies associated with the specified employee.
     */
    public List<VacancyShortResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toShortDTO
        );
    }
}
