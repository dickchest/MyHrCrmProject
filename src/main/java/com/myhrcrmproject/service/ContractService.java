package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Contract;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import com.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import com.myhrcrmproject.repository.CandidateRepository;
import com.myhrcrmproject.repository.ClientRepository;
import com.myhrcrmproject.repository.ContractRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.service.utills.ContractConverter;
import com.myhrcrmproject.service.utills.Helper;
import com.myhrcrmproject.service.validation.NotFoundException;
import com.myhrcrmproject.domain.enums.ContractType;
import com.myhrcrmproject.service.auth.SecurityHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for contract entities.
 *
 * <p>This service provides methods to perform operations on contract entities, such as retrieving,
 * creating, updating, and deleting contract records. It also includes additional methods for finding
 * contract records based on different criteria.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ContractService implements CommonService<ContractRequestDTO, ContractResponseDTO> {
    private final ContractRepository repository;
    private final ContractConverter converter;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final CandidateRepository candidateRepository;
    private final SecurityHelper securityHelper;

    /**
     * Retrieves a list of all contract records.
     *
     * @return A list of response DTOs representing all contract records.
     */
    public List<ContractResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a contract record by its unique identifier.
     *
     * @param id The identifier of the contract record to retrieve.
     * @return The response DTO representing the retrieved contract record.
     * @throws NotFoundException if the contract record with the specified id is not found.
     */
    public ContractResponseDTO findById(Integer id) {
        Contract entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    /**
     * Creates a new contract record based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new contract record.
     * @return The response DTO representing the newly created contract record.
     */
    public ContractResponseDTO create(ContractRequestDTO requestDTO) {
        Contract entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // extra methods
        // set current auth user as employee
        Optional<Employee> employee = securityHelper.getCurrentAuthEmployee();
        employee.ifPresent(entity::setEmployee);


        entity.setCreateDate(LocalDateTime.now());
        entity.setUpdateDate(LocalDateTime.now());

        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing contract record with new data.
     *
     * @param id         The identifier of the contract record to update.
     * @param requestDTO The request DTO containing updated data for the contract record.
     * @return The response DTO representing the updated contract record.
     * @throws NotFoundException if the contract record with the specified id is not found.
     */
    public ContractResponseDTO update(Integer id, ContractRequestDTO requestDTO) {
        Contract existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        // do extra procedures
        // remember employee who updated the entry

        Optional<Employee> employee = securityHelper.getCurrentAuthEmployee();
        employee.ifPresent(existingEntity::setEmployee);

        existingEntity.setUpdateDate(LocalDateTime.now());

        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a contract record by its unique identifier.
     *
     * @param id The identifier of the contract record to delete.
     * @throws NotFoundException if the contract record with the specified id is not found.
     */
    public void delete(Integer id) {
        Contract entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contract with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Retrieves a list of contract records based on the type of contract.
     *
     * @param id The identifier of the contract type.
     * @return A list of response DTOs representing contract records of the specified type.
     */
    public List<ContractResponseDTO> findAllByContractTypeId(Integer id) {
        return Helper.findAllByEnumId(
                id,
                ContractType.class,
                repository::findAllByContractType,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of contracts associated with a specific employee.
     *
     * @param id The identifier of the employee to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing contracts associated with the specified employee.
     */
    public List<ContractResponseDTO> findAllByEmployeeId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                employeeRepository,
                repository::findByEmployee,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of contracts associated with a specific client.
     *
     * @param id The identifier of the client to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing contracts associated with the specified client.
     */
    public List<ContractResponseDTO> findAllByClientId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                clientRepository,
                repository::findByClient,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of contracts associated with a specific candidate.
     *
     * @param id The identifier of the candidate to filter by.
     * @return A list of {@code CommunicationResponseDTO} representing contracts associated with the specified candidate.
     */
    public List<ContractResponseDTO> findAllByCandidateId(Integer id) {
        return Helper.findAllByEntityId(
                id,
                candidateRepository,
                repository::findByCandidate,
                converter::toDTO
        );
    }

    /**
     * Retrieves a list of all active contract records.
     *
     * @return A list of {@code CommunicationResponseDTO} representing all active contract records.
     */
    public List<ContractResponseDTO> findAllActiveContracts() {
        LocalDate date = LocalDate.now();
        List<Contract> list = repository.findByStartDateBeforeAndEndDateAfter(
                date.plusDays(1), date.minusDays(1));
        return list.stream()
                .map(converter::toDTO)
                .toList();
    }

    //todo: добавить методы для поиска заканчивающихся контрактов
}
