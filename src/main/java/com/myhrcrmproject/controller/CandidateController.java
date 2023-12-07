package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.service.CandidateService;
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
 * Controller class for managing candidate-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting candidate records. It interacts with the {@code CandidateService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding candidates by their status
 * and by the associated vacancy. The {@code IsManager} annotation is used to
 * secure the delete operation, requiring the manager role.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role as
 * a Spring REST controller and is mapped to the "/api/candidates" endpoint.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@RestController
@RequestMapping("api/candidates")
@AllArgsConstructor
@Getter
@Tag(name = "Candidate Controller", description = "APIs for managing candidates")
public class CandidateController {
    private final CandidateService service;

    /**
     * Endpoint to retrieve a list of all candidates.
     *
     * @return A {@code ResponseEntity} with a list of {@code CandidateResponseDTO} representing all candidates.
     */
    @GetMapping
    @Operation(
            summary = "Get all candidates",
            description = "Retrieve a list of all candidates."
    )
    public ResponseEntity<List<CandidateResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a candidate by their ID.
     *
     * @param id The ID of the candidate to retrieve.
     * @return A {@code ResponseEntity} with the {@code CandidateResponseDTO} representing the retrieved candidate.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get candidate by ID",
            description = "Retrieve a candidate by their ID."
    )
    public ResponseEntity<CandidateResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to create a new candidate.
     *
     * @param requestDTO The request body containing candidate details.
     * @return A {@code ResponseEntity} with the {@code CandidateResponseDTO} representing the newly created candidate.
     */
    @PostMapping
    @Operation(
            summary = "Create a new candidate",
            description = "Create a new candidate with the provided details."
    )
    public ResponseEntity<CandidateResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing candidate details")
            CandidateRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    /**
     * Endpoint to update an existing candidate.
     *
     * @param id         The ID of the candidate to update.
     * @param requestDTO The request body containing updated candidate details.
     * @return A {@code ResponseEntity} with the {@code CandidateResponseDTO} representing the updated candidate.
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a candidate",
            description = "Update an existing candidate with the provided details."
    )
    public ResponseEntity<CandidateResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate to update", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated candidate details")
            CandidateRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a candidate. Requires the manager role.
     *
     * @param id The ID of the candidate to delete.
     */
    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a candidate",
            description = "Delete a candidate by their ID. Requires manager role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }


    /**
     * Endpoint to retrieve a list of candidates by status ID.
     *
     * @param statusID The ID of the status to filter by.
     * @return A {@code ResponseEntity} with a list of {@code CandidateShortResponseDTO} representing candidates with the specified status.
     */
    @GetMapping("/findAllByStatus/{id}")
    @Operation(
            summary = "Get candidates by status ID",
            description = "Retrieve a list of candidates by status ID."
    )
    public ResponseEntity<List<CandidateShortResponseDTO>> findAllByStatusId(
            @PathVariable("id")
            @Parameter(description = "ID of the status",example = "1")
            Integer statusID) {
        return new ResponseEntity<>(service.findAllByStatusId(statusID), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a list of candidates by vacancy ID.
     *
     * @param id The ID of the vacancy to filter by.
     * @return A {@code ResponseEntity} with a list of {@code CandidateShortResponseDTO} representing candidates associated with the specified vacancy.
     */
    @GetMapping("/findAllByVacancy/{id}")
    @Operation(
            summary = "Get candidates by vacancy ID",
            description = "Retrieve a list of candidates by vacancy ID."
    )
    public ResponseEntity<List<CandidateShortResponseDTO>> findAllByVacancyId(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }
}
