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

@RestController
@RequestMapping("api/candidates")
@AllArgsConstructor
@Getter
@Tag(name = "Candidate Controller", description = "APIs for managing candidates")
public class CandidateController {
    private final CandidateService service;

    @GetMapping
    @Operation(
            summary = "Get all candidates",
            description = "Retrieve a list of all candidates."
    )
    public ResponseEntity<List<CandidateResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

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
