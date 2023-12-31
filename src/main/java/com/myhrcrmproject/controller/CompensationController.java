package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import com.myhrcrmproject.service.CompensationService;
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
 * Controller class for managing compensation-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting compensation records. It interacts with the {@code CompensationService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding compensations by their ID,
 * retrieving a list of all compensations, creating a new compensation, updating an
 * existing compensation, and deleting a compensation. There are also endpoints for
 * finding compensations by type ID, employee ID, client ID, candidate ID, and vacancy ID.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role
 * as a Spring REST controller and is mapped to the "/api/compensations" endpoint.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@RestController
@RequestMapping("api/compensations")
@AllArgsConstructor
@Getter
@Tag(name = "Compensation Controller", description = "APIs for managing compensations")
public class CompensationController {
    private final CompensationService service;

    /**
     * Endpoint to retrieve a list of all compensations.
     *
     * @return A {@code ResponseEntity} with a list of {@code CompensationResponseDTO} representing all compensations.
     */
    @GetMapping
    @Operation(
            summary = "Get all compensations",
            description = "Retrieve a list of all compensations."
    )
    public ResponseEntity<List<CompensationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a compensation by its ID.
     *
     * @param id The ID of the compensation to retrieve.
     * @return A {@code ResponseEntity} with the {@code CompensationResponseDTO} representing the retrieved compensation.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get compensation by ID",
            description = "Retrieve a compensation by their ID."
    )
    public ResponseEntity<CompensationResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the compensation", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to create a new compensation.
     *
     * @param requestDTO The request body containing compensation details.
     * @return A {@code ResponseEntity} with the {@code CompensationResponseDTO} representing the newly created compensation.
     */
    @PostMapping
    @Operation(
            summary = "Create a new compensation",
            description = "Create a new compensation with the provided details."
    )
    public ResponseEntity<CompensationResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing compensation details")
            CompensationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing compensation.
     *
     * @param id         The ID of the compensation to update.
     * @param requestDTO The request body containing updated compensation details.
     * @return A {@code ResponseEntity} with the {@code CompensationResponseDTO} representing the updated compensation.
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a compensation",
            description = "Update an existing compensation with the provided details."
    )
    public ResponseEntity<CompensationResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the compensation", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated compensation details")
            CompensationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a compensation.
     *
     * @param id The ID of the compensation to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @IsManager
    @Operation(
            summary = "Delete a compensation",
            description = "Delete a compensation by their ID. Requires manager role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the compensation to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

    /**
     * Endpoint to retrieve a list of compensations by candidate ID.
     *
     * @param id The ID of the candidate.
     * @return A {@code ResponseEntity} with a list of {@code CompensationResponseDTO} representing compensations by candiddate ID.
     */
    @GetMapping("/findAllByCandidate/{id}")
    @Operation(
            summary = "Get compensations by candidate ID",
            description = "Retrieve a list of compensations by candidate ID."
    )
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByCandidateId(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of compensations by contract ID.
     *
     * @param id The ID of the contract.
     * @return A {@code ResponseEntity} with a list of {@code CompensationResponseDTO} representing compensations by contract ID.
     */
    @GetMapping("/findAllByContract/{id}")
    @Operation(
            summary = "Get compensations by contract ID",
            description = "Retrieve a list of compensations by contract ID."
    )
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByContractId(
            @PathVariable("id")
            @Parameter(description = "ID of the contract", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByContractId(id), HttpStatus.OK);
    }
}
