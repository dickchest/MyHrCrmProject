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

@RestController
@RequestMapping("api/compensations")
@AllArgsConstructor
@Getter
@Tag(name = "Compensation Controller", description = "APIs for managing compensations")
public class CompensationController {
    private final CompensationService service;

    @GetMapping
    @Operation(
            summary = "Get all compensations",
            description = "Retrieve a list of all compensations."
    )
    public ResponseEntity<List<CompensationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

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
