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

    @GetMapping
    @Operation(
            summary = "Get all employees",
            description = "Retrieve a list of all employees."
    )
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

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
