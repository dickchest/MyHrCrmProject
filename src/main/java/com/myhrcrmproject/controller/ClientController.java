package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import com.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import com.myhrcrmproject.service.ClientService;
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
@RequestMapping("api/clients")
@AllArgsConstructor
@Getter
@Tag(name = "Client Controller", description = "APIs for managing clients")
public class ClientController {
    private final ClientService service;

    @GetMapping
    @Operation(
            summary = "Get all clients",
            description = "Retrieve a list of all clients."
    )
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get client by ID",
            description = "Retrieve a client by their ID."
    )
    public ResponseEntity<ClientResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the client", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create a new client",
            description = "Create a new client with the provided details."
    )
    public ResponseEntity<ClientResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing client details")
            ClientRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a client",
            description = "Update an existing client with the provided details."
    )
    public ResponseEntity<ClientResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the client to update", example = "1") Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing client details")
            ClientRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a client",
            description = "Delete a client by their ID. Requires manager role."
    )
    public void deleteCandidate(
            @PathVariable("id")
            @Parameter(description = "ID of the client to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

}
