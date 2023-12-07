package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller class for managing user details-related operations in the HR CRM system.
 *
 * <p>This class defines RESTful APIs for various user details actions, including
 * retrieving all user details, finding user details by ID, updating user details,
 * deleting user details, and setting user roles. It requires an admin role for access.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
@Slf4j
@Tag(
        name = "User Details Controller",
        description = "APIs for managing user details. Requires admin role."
)
public class UserDetailsController {
    private final UserDetailsServiceImpl service;

    /**
     * Endpoint to retrieve a list of all user details.
     *
     * @return A {@code ResponseEntity} with a list of {@code UserDetailsResponseDTO} representing all user details.
     */
    @GetMapping
    @Operation(
            summary = "Get all user details",
            description = "Retrieve a list of all user details. Requires admin role."
    )
    public ResponseEntity<List<UserDetailsResponseDTO>> findAll() {
        log.info("ENTER FIND ALL CONTROLLER");
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a user detail by its ID.
     *
     * @param id The ID of the user detail to retrieve.
     * @return A {@code ResponseEntity} with the {@code UserDetailsResponseDTO} representing the retrieved user detail.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user detail by ID",
            description = "Retrieve an user detail by their ID. Requires admin role."
    )
    public ResponseEntity<UserDetailsResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to update an existing user detail.
     *
     * @param id         The ID of the user detail to update.
     * @param requestDTO The request body containing updated user detail.
     * @return A {@code ResponseEntity} with the {@code UserDetailsResponseDTO} representing the updated user detail.
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a user detail",
            description = "Update an existing user detail with the provided details. Requires admin role."
    )
    public ResponseEntity<UserDetailsResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated user detail details")
            UserDetailsRequestDTO requestDTO) {
        UserDetailsResponseDTO responseDTO = service.update(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a user detail.
     *
     * @param id The ID of the user detail to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete an user detail",
            description = "Delete an user detail by their ID. Requires admin role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail to delete", example = "1")
            Integer id) {
        service.delete(id);
    }

    /**
     * Updates an user detail's role. Requires admin role.
     *
     * @param id      The ID of the user detail to update.
     * @param request The role to set for the user detail.
     * @return A {@code ResponseEntity} with the updated user detail details and HTTP status code 202 (Accepted).
     */
    @PutMapping("/setRole") // запрос: api/users/setRole?id=2&role=manager
    @Operation(
            summary = "Update an user details role",
            description = "Update an existing user details with the provided role. Requires admin role."
    )
    public ResponseEntity<UserDetailsShortResponseDTO> setRole(
            @RequestParam("id")
            @Parameter(description = "ID of the user detail", example = "2")
            Integer id,
            @RequestParam("role")
            @Parameter(description = "role", example = "admin")
            String request) {
        log.info("Set Role controller has been called");
        return new ResponseEntity<>(service.setRole(id, request), HttpStatus.ACCEPTED);
    }

}
