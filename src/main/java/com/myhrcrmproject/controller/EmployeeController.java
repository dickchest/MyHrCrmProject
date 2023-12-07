package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsAdministrator;
import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import com.myhrcrmproject.service.EmployeeService;
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
 * Controller class for managing employee-related operations in the HR CRM system.
 *
 * <p>This class provides RESTful endpoints for retrieving, creating, updating,
 * and deleting employee records. It interacts with the {@code EmployeeService}
 * for handling business logic and data access.
 *
 * <p>Additionally, it includes endpoints for finding employees by their ID,
 * retrieving a list of all employees, creating a new employee (with administrator role required),
 * updating an existing employee, and deleting an employee (with manager role required).
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role
 * as a Spring REST controller and is mapped to the "/api/employees" endpoint.
 *
 * <p>Author: Denys Chaykovskyy
 *
 * @version 1.0
 */
@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
@Getter
@Tag(
        name = "Employee Controller",
        description = "APIs for managing employees"
)
public class EmployeeController {
    private final EmployeeService service;

    /**
     * Endpoint to retrieve a list of all communications.
     *
     * @return A {@code ResponseEntity} with a list of {@code CommunicationResponseDTO} representing all communications.
     */
    @GetMapping
    @Operation(
            summary = "Get all employees",
            description = "Retrieve a list of all employees."
    )
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a communication by its ID.
     *
     * @param id The ID of the communication to retrieve.
     * @return A {@code ResponseEntity} with the {@code CommunicationResponseDTO} representing the retrieved communication.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get employee by ID",
            description = "Retrieve a employee by their ID."
    )
    public ResponseEntity<EmployeeResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    /**
     * Endpoint to create a new communication.
     *
     * @param requestDTO The request body containing communication details.
     * @return A {@code ResponseEntity} with the {@code CommunicationResponseDTO} representing the newly created communication.
     */
    @IsAdministrator
    @PostMapping
    @Operation(
            summary = "Create a new employee",
            description = "Create a new employee with the provided details. Requires administrator role."
    )
    public ResponseEntity<EmployeeResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing employee details")
            EmployeeRequestDTO requestDTO) {
        return new ResponseEntity<>(service.create(requestDTO), HttpStatus.CREATED);
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
            summary = "Update a employee",
            description = "Update an existing employee with the provided details."
    )
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated employee details")
            EmployeeRequestDTO requestDTO) {
        return new ResponseEntity<>(service.update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    /**
     * Endpoint to delete a communication. Requires manager role.
     *
     * @param id The ID of the communication to delete.
     */
    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a employee",
            description = "Delete a employee by their ID. Requires manager role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the employee to delete", example = "1")
            Integer id) {
        service.delete(id);
    }
}
