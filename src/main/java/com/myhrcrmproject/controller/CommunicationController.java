package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import com.myhrcrmproject.service.CommunicationService;
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
 * Controller class for managing communication-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting communication records. It interacts with the {@code CommunicationService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding communications by their ID,
 * retrieving a list of all communications, creating a new communication, updating an
 * existing communication, and deleting a communication. There are also endpoints for
 * finding communications by type ID, employee ID, client ID, candidate ID, and vacancy ID.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role
 * as a Spring REST controller and is mapped to the "/api/communications" endpoint.
 *
 * <p>Author: Denys Chaykovskyy
 *
 * @version 1.0
 */
@RestController
@RequestMapping("api/communications")
@AllArgsConstructor
@Getter
@Tag(
        name = "Communication Controller",
        description = "APIs for managing communications"
)
public class CommunicationController {
    private final CommunicationService service;

    /**
     * Endpoint to retrieve a list of all communications.
     *
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing all communications.
     */
    @GetMapping
    @Operation(
            summary = "Get all communications",
            description = "Retrieve a list of all communications."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a communication by its ID.
     *
     * @param id The ID of the communication to retrieve.
     * @return A {@code ResponseEntity} with the {@code CommunicationResponseDTO} representing the retrieved communication.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get communication by ID",
            description = "Retrieve a communication by their ID."
    )
    public ResponseEntity<CommunicationResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the communication", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to create a new communication.
     *
     * @param requestDTO The request body containing communication details.
     * @return A {@code ResponseEntity} with the {@code CommunicationResponseDTO} representing the newly created communication.
     */
    @PostMapping
    @Operation(
            summary = "Create a new communication",
            description = "Create a new communication with the provided details."
    )
    public ResponseEntity<CommunicationResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing communication details")
            CommunicationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing communication.
     *
     * @param id         The ID of the communication to update.
     * @param requestDTO The request body containing updated communication details.
     * @return A {@code ResponseEntity} with the {@code CommunicationResponseDTO} representing the updated communication.
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a communication",
            description = "Update an existing communication with the provided details."
    )
    public ResponseEntity<CommunicationResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the communication", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated communication details")
            CommunicationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a communication.
     *
     * @param id The ID of the communication to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a communication",
            description = "Delete a communication by their ID."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the communication to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

    /**
     * Endpoint to retrieve a list of communications by type ID.
     *
     * @param id The ID of the type.
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing communications by type ID.
     */
    @GetMapping("/findAllByCommunicationTypeId/{id}")
    @Operation(
            summary = "Get communications by type ID",
            description = "Retrieve a list of communications by type ID."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByCommunicationTypeId(
            @PathVariable("id")
            @Parameter(description = "ID of the type",example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByCommunicationTypeId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of communications by employee ID.
     *
     * @param id The ID of the employee.
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing communications by employee ID.
     */
    @GetMapping("/findAllByEmployee/{id}")
    @Operation(
            summary = "Get communications by employee ID",
            description = "Retrieve a list of communications by employee ID."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of communications by client ID.
     *
     * @param id The ID of the client.
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing communications by client ID.
     */
    @GetMapping("/findAllByClient/{id}")
    @Operation(
            summary = "Get communications by client ID",
            description = "Retrieve a list of communications by client ID."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByClientId(
            @PathVariable("id")
            @Parameter(description = "ID of the client", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByClientId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of communications by candidate ID.
     *
     * @param id The ID of the candidate.
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing communications by candidate ID.
     */
    @GetMapping("/findAllByCandidate/{id}")
    @Operation(
            summary = "Get communications by candidate ID",
            description = "Retrieve a list of communications by candidate ID."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByCandidateId(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of communications by vacancy ID.
     *
     * @param id The ID of the vacancy.
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing communications by vacancy ID.
     */
    @GetMapping("/findAllByVacancy/{id}")
    @Operation(
            summary = "Get communications by vacancy ID",
            description = "Retrieve a list of communications by vacancy ID."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByVacancyId(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }
}
