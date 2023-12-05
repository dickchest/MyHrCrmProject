package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import com.myhrcrmproject.service.VacancyService;
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
@RequestMapping("api/vacancies")
@AllArgsConstructor
@Getter
@Tag(
        name = "Vacancy Controller",
        description = "APIs for managing vacancies"
)
public class VacancyController {
    private final VacancyService service;

    @GetMapping
    @Operation(
            summary = "Get all vacancies",
            description = "Retrieve a list of all vacancies."
    )
    public ResponseEntity<List<VacancyResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get vacancy by ID",
            description = "Retrieve a vacancy by their ID."
    )
    public ResponseEntity<VacancyResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @IsManager
    @PostMapping
    @Operation(
            summary = "Create a new vacancy",
            description = "Create a new vacancy with the provided details. Requires manager role."
    )
    public ResponseEntity<VacancyResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing vacancy details")
            VacancyRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @IsManager
    @PutMapping("/{id}")
    @Operation(
            summary = "Update a vacancy",
            description = "Update an existing vacancy with the provided details. Requires manager role."
    )
    public ResponseEntity<VacancyResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated vacancy details")
            VacancyRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a vacancy",
            description = "Delete a vacancy by their ID."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }


    @GetMapping("/findAllByStatus/{id}")
    @Operation(
            summary = "Get vacancies by status ID",
            description = "Retrieve a list of vacancies by status ID. Requires manager role."
    )
    public ResponseEntity<List<VacancyResponseDTO>> findAllByStatus(
            @PathVariable("id")
            @Parameter(description = "ID of the status", example = "1")
            Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatusId(statusId), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    @Operation(
            summary = "Get vacancies by employee ID",
            description = "Retrieve a list of vacancies by employee ID."
    )
    public ResponseEntity<List<VacancyShortResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }
}
