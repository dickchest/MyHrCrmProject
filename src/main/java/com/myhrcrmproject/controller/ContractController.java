package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import com.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import com.myhrcrmproject.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for managing contract-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting contract records. It interacts with the {@code ContractService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding contracts by their ID,
 * retrieving a list of all contracts, creating a new contract, updating an
 * existing contract, and deleting a contract. There are also endpoints for
 * finding contracts by type ID, employee ID, client ID, and candidate ID.
 * Additionally, it provides endpoints for finding all active contracts by candidate ID
 * and retrieving a list of all active contracts.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role
 * as a Spring REST controller and is mapped to the "/api/contracts" endpoint.
 *
 * <p>Author: Denys Chaykovskyy
 *
 * @version 1.0
 */
@RestController
@RequestMapping("api/contracts")
@AllArgsConstructor
@Getter
@Tag(
        name = "Contract Controller",
        description = "APIs for managing contracts"
)
public class ContractController {
    private final ContractService service;

    /**
     * Endpoint to retrieve a list of all contracts.
     *
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing all contracts.
     */
    @GetMapping
    @Operation(
            summary = "Get all contracts",
            description = "Retrieve a list of all contracts."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a contract by its ID.
     *
     * @param id The ID of the contract to retrieve.
     * @return A {@code ResponseEntity} with the {@code ContractResponseDTO} representing the retrieved contract.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get contract by ID",
            description = "Retrieve a contract by their ID."
    )
    public ResponseEntity<ContractResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the contract", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to create a new contract.
     *
     * @param requestDTO The request body containing contract details.
     * @return A {@code ResponseEntity} with the {@code ContractResponseDTO} representing the newly created contract.
     */
    @PostMapping
    @Operation(
            summary = "Create a new contract",
            description = "Create a new contract with the provided details."
    )
    public ResponseEntity<ContractResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing contract details")
            ContractRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing contract.
     *
     * @param id         The ID of the contract to update.
     * @param requestDTO The request body containing updated contract details.
     * @return A {@code ResponseEntity} with the {@code ContractResponseDTO} representing the updated contract.
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a contract",
            description = "Update an existing contract with the provided details."
    )
    public ResponseEntity<ContractResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the contract", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated contract details")
            ContractRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a contract. Requires manager role.
     *
     * @param id The ID of the contract to delete.
     */
    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a contract",
            description = "Delete a contract by their ID. Requires manager role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the contract to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

    /**
     * Endpoint to retrieve a list of contracts by type ID.
     *
     * @param id The ID of the contract type.
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing contracts by type ID.
     */
    @GetMapping("/findAllByContractTypeId/{id}")
    @Operation(
            summary = "Get contracts by type ID",
            description = "Retrieve a list of contracts by type ID."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllByContractTypeId(
            @PathVariable("id")
            @Parameter(description = "ID of the type", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByContractTypeId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of contracts by employee ID.
     *
     * @param id The ID of the employee.
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing contracts by employee ID.
     */
    @GetMapping("/findAllByEmployee/{id}")
    @Operation(
            summary = "Get contracts by employee ID",
            description = "Retrieve a list of contracts by employee ID."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of contracts by client ID.
     *
     * @param id The ID of the client.
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing contracts by client ID.
     */
    @GetMapping("/findAllByClient/{id}")
    @Operation(
            summary = "Get contracts by client ID",
            description = "Retrieve a list of contracts by client ID."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllByClientId(
            @PathVariable("id")
            @Parameter(description = "ID of the client", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByClientId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of contracts by candidate ID.
     *
     * @param id The ID of the candidate.
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing contracts by candidate ID.
     */
    @GetMapping("/findAllByCandidate/{id}")
    @Operation(
            summary = "Get contracts by candidate ID",
            description = "Retrieve a list of contracts by candidate ID."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllActiveContracts(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of all active contracts.
     *
     * @return A {@code ResponseEntity} with a list of {@code ContractResponseDTO} representing all active contracts.
     */
    @GetMapping("/findAllActiveContracts")
    @Operation(
            summary = "Get all active contracts",
            description = "Retrieve a list of all active contracts."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllActiveContracts() {
        return new ResponseEntity<>(service.findAllActiveContracts(), HttpStatus.OK);
    }
}
