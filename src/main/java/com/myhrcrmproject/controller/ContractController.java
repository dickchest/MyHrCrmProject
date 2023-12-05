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

    @GetMapping
    @Operation(
            summary = "Get all contracts",
            description = "Retrieve a list of all contracts."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

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

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a contract",
            description = "Delete a contract by their ID."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the contract to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

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

    @GetMapping("/findAllActiveContracts")
    @Operation(
            summary = "Get all active contracts",
            description = "Retrieve a list of all active contracts."
    )
    public ResponseEntity<List<ContractResponseDTO>> findAllActiveContracts() {
        return new ResponseEntity<>(service.findAllActiveContracts(), HttpStatus.OK);
    }
}
