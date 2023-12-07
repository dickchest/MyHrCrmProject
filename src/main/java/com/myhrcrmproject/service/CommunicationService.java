package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Communication;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.enums.CommunicationType;
import com.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import com.myhrcrmproject.repository.*;
import com.myhrcrmproject.service.auth.SecurityHelper;
import com.myhrcrmproject.service.utills.CommunicationConverter;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for communication entities.
 *
 * <p>This service provides methods to perform operations on communication entities, such as retrieving,
 * creating, updating, and deleting communication records. It also includes additional methods for finding
 * communication records based on different criteria.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CommunicationService implements CommonService<CommunicationRequestDTO, CommunicationResponseDTO> {
    private final CommunicationRepository repository;
    private final CommunicationConverter converter;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final SecurityHelper securityHelper;

    /**
     * Retrieves a list of all communication records.
     *
     * @return A list of response DTOs representing all communication records.
     */
    public List<CommunicationResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a communication record by its unique identifier.
     *
     * @param id The identifier of the communication record to retrieve.
     * @return The response DTO representing the retrieved communication record.
     * @throws NotFoundException if the communication record with the specified id is not found.
     */
    public CommunicationResponseDTO findById(Integer id) {
        Communication entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id: " + id + " not found!"));
        return converter.toDTO(entity);
    }

    /**
     * Creates a new communication record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new communication record.
     * @return The response DTO representing the newly created communication record.
     */
    public CommunicationResponseDTO create(CommunicationRequestDTO requestDTO) {

        Communication entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // if date&time haven't been adjusted, set current date&time
        if (requestDTO.getCommunicationDateTime() == null) {
            entity.setCommunicationDateTime(LocalDateTime.now());
        }

        // автоматически заносился employee кто меняет эту запись
        if (requestDTO.getEmployeeId() == null) {
            Optional<Employee> optionalEmployee = securityHelper.getCurrentAuthEmployee();
            optionalEmployee.ifPresent(entity::setEmployee);
        }

        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing communication record with new data.
     *
     * @param id         The identifier of the communication record to update.
     * @param requestDTO The request DTO containing updated data for the communication record.
     * @return The response DTO representing the updated communication record.
     * @throws NotFoundException if the communication record with the specified id is not found.
     */
    public CommunicationResponseDTO update(Integer id, CommunicationRequestDTO requestDTO) {
        Communication existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // ....
        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a communication record by its unique identifier.
     *
     * @param id The identifier of the communication record to delete.
     * @throws NotFoundException if the communication record with the specified id is not found.
     */
    public void delete(Integer id) {
        Communication entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Communication with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Retrieves a list of communication records based on the type of communication.
     *
     * @param id The identifier of the communication type.
     * @return A list of response DTOs representing communication records of the specified type.
     */
    public List<CommunicationResponseDTO> findAllByCommunicationTypeId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                CommunicationType.class,
                repository::findAllByCommunicationType,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of communications associated with a specific employee.
     *
     * @param id The identifier of the employee to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing communications associated with the specified employee.
     */
    // find All by Employee id
    public List<CommunicationResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of communications associated with a specific client.
     *
     * @param id The identifier of the client to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing communications associated with the specified client.
     */
    public List<CommunicationResponseDTO> findAllByClientId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                clientRepository,
                repository::findByClient,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of communications associated with a specific candidate.
     *
     * @param id The identifier of the candidate to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing communications associated with the specified candidate.
     */
    public List<CommunicationResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of communications associated with a specific vacancy.
     *
     * @param id The identifier of the vacancy to filter by.
     * @return A list of {@code CandidateShortResponseDTO} representing communications associated with the specified vacancy.
     */
    public List<CommunicationResponseDTO> findAllByVacancyId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                vacancyRepository,
                repository::findByVacancy,
                converter::toDTO
        );
    }
}
