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

    @GetMapping
    @Operation(
            summary = "Get all communications",
            description = "Retrieve a list of all communications."
    )
    public ResponseEntity<List<CommunicationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

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
