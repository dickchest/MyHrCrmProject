package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import com.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import com.myhrcrmproject.service.InterviewService;
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
@RequestMapping("api/interviews")
@AllArgsConstructor
@Getter
@Tag(
        name = "Interview Controller",
        description = "APIs for managing interviews"
)
public class InterviewController {
    private final InterviewService service;

    @GetMapping
    @Operation(
            summary = "Get all interviews",
            description = "Retrieve a list of all interviews."
    )
    public ResponseEntity<List<InterviewResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get interview by ID",
            description = "Retrieve a interview by their ID."
    )
    public ResponseEntity<InterviewResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the interview", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create a new interview",
            description = "Create a new interview with the provided details."
    )
    public ResponseEntity<InterviewResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing interview details")
            InterviewRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an interview",
            description = "Update an existing interview with the provided details."
    )
    public ResponseEntity<InterviewResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the interview", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated interview details")
            InterviewRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete an interview",
            description = "Delete an interview by their ID."
    )
    public void deleteCandidate(
            @PathVariable("id")
            @Parameter(description = "ID of the interview to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

    @GetMapping("/findAllByStatus/{id}")
    @Operation(
            summary = "Get interviews by status ID",
            description = "Retrieve a list of interviews by status ID."
    )
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByStatusId(
            @PathVariable("id")
            @Parameter(description = "ID of the status", example = "1")
            Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatusId(statusId), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    @Operation(
            summary = "Get interviews by employee ID",
            description = "Retrieve a list of interviews by employee ID."
    )
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @PutMapping("/findAllByDateAndEmployee/{id}")
    @Operation(
            summary = "Get interviews by date and employee ID",
            description = "Retrieve a list of interviews by date and employee ID."
    )
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing date")
            InterviewDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDateAndEmployeeId(requestDTO, id), HttpStatus.OK);
    }

    @PutMapping("/findAllByDate")
    @Operation(
            summary = "Get interviews by date",
            description = "Retrieve a list of interviews by date."
    )
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByDate(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing date")
            InterviewDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDate(requestDTO), HttpStatus.OK);
    }
}
