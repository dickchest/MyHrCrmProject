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
/**
 * Controller class for managing client-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting client records. It interacts with the {@code ClientService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding clients by their ID,
 * retrieving a list of all clients, creating a new client, updating an
 * existing client, and deleting a client. The {@code IsManager} annotation
 * is used to secure the delete operation, requiring the manager role.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role
 * as a Spring REST controller and is mapped to the "/api/clients" endpoint.
 *
 * <p>Author: Denys Chaykovskyy
 *
 * @version 1.0
 */
@RestController
@RequestMapping("api/clients")
@AllArgsConstructor
@Getter
@Tag(name = "Client Controller", description = "APIs for managing clients")
public class ClientController {
    private final ClientService service;

    /**
     * Endpoint to retrieve a list of all clients.
     *
     * @return A {@code ResponseEntity} with a list of {@code ClientResponseDTO} representing all clients.
     */
    @GetMapping
    @Operation(
            summary = "Get all clients",
            description = "Retrieve a list of all clients."
    )
    public ResponseEntity<List<ClientResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a client by their ID.
     *
     * @param id The ID of the client to retrieve.
     * @return A {@code ResponseEntity} with the {@code ClientResponseDTO} representing the retrieved client.
     */
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

    /**
     * Endpoint to create a new client.
     *
     * @param requestDTO The request body containing client details.
     * @return A {@code ResponseEntity} with the {@code ClientResponseDTO} representing the newly created client.
     */
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

    /**
     * Endpoint to update an existing client.
     *
     * @param id         The ID of the client to update.
     * @param requestDTO The request body containing updated client details.
     * @return A {@code ResponseEntity} with the {@code ClientResponseDTO} representing the updated client.
     */
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

    /**
     * Endpoint to delete a client. Requires the manager role.
     *
     * @param id The ID of the client to delete.
     */
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
