package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import com.myhrcrmproject.service.TaskService;
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
@RequestMapping("api/tasks")
@AllArgsConstructor
@Getter
@Tag(
        name = "Task Controller",
        description = "APIs for managing tasks"
)
public class TaskController {
    private final TaskService service;

    @IsManager
    @GetMapping
    @Operation(
            summary = "Get all tasks",
            description = "Retrieve a list of all tasks. Requires manager role."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get task by ID",
            description = "Retrieve a task by their ID."
    )
    public ResponseEntity<TaskResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the task", example = "1")
            Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Create a new task with the provided details."
    )
    public ResponseEntity<TaskResponseDTO> createNew(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing task details")
            TaskRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a task",
            description = "Update an existing task with the provided details."
    )
    public ResponseEntity<TaskResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the task", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated task details")
            TaskRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a task",
            description = "Delete a task by their ID."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the task to delete", example = "1")
            Integer id) {
        getService().delete(id);
    }

    @IsManager
    @GetMapping("/findAllByTaskStatusId/{id}")
    @Operation(
            summary = "Get tasks by status ID",
            description = "Retrieve a list of tasks by status ID. Requires manager role."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAllByTaskStatusId(
            @PathVariable("id")
            @Parameter(description = "ID of the status", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByTaskStatusId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    @Operation(
            summary = "Get tasks by employee ID",
            description = "Retrieve a list of tasks by employee ID."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAllByEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @IsManager
    @GetMapping("/findAllByCandidate/{id}")
    @Operation(
            summary = "Get tasks by candidate ID",
            description = "Retrieve a list of tasks by candidate ID. Requires manager role."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAllByCandidateId(
            @PathVariable("id")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @IsManager
    @GetMapping("/findAllByVacancy/{id}")
    @Operation(
            summary = "Get tasks by vacancy ID",
            description = "Retrieve a list of tasks by vacancy ID. Requires manager role."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAllByVacancyId(
            @PathVariable("id")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }

    @IsManager
    @PutMapping("/findAllByStartDate")
    @Operation(
            summary = "Get tasks by start date",
            description = "Retrieve a list of tasks by start date. Requires manager role."
    )
    public ResponseEntity<List<TaskResponseDTO>> findAllByStartDate(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing start date")
            TaskDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByStartDate(requestDTO), HttpStatus.OK);
    }

    @PutMapping("/findAllByStartDateAndEmployee/{id}")
    @Operation(
            summary = "Get tasks by start date and employee ID",
            description = "Retrieve a list of tasks by start date and employee ID."
    )
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByStartDateAndEmployeeId(
            @PathVariable("id")
            @Parameter(description = "ID of the employee", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing start date")
            TaskDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDateAndEmployeeId(id, requestDTO), HttpStatus.OK);
    }

    @GetMapping("/findAllByCandidateAndEmployee/{candidateId}/{employeeId}")
    @Operation(
            summary = "Get tasks by candidate ID and employee ID",
            description = "Retrieve a list of tasks by candidate ID and employee ID."
    )
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByCandidateIdAndEmployeeId(
            @PathVariable("candidateId")
            @Parameter(description = "ID of the candidate", example = "1")
            Integer candidateId,
            @PathVariable("employeeId")
            @Parameter(description = "ID of the employee", example = "1")
            Integer employeeId) {
        return new ResponseEntity<>(service.findAllByCandidateIdAndEmployeeId(candidateId, employeeId), HttpStatus.OK);
    }

    @GetMapping("/findAllByStatusAndEmployee/{statusId}/{employeeId}")
    @Operation(
            summary = "Get tasks by status ID and employee ID",
            description = "Retrieve a list of tasks by status ID and employee ID."
    )
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByStatusIdAndEmployeeId(
            @PathVariable("statusId")
            @Parameter(description = "ID of task status", example = "1")
            Integer statusId,
            @PathVariable("employeeId")
            @Parameter(description = "ID of the employee", example = "1")
            Integer employeeId) {
        return new ResponseEntity<>(service.findAllByStatusIdAndEmployeeId(statusId, employeeId), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancyAndEmployee/{vacancyId}/{employeeId}")
    @Operation(
            summary = "Get tasks by vacancy ID and employee ID",
            description = "Retrieve a list of tasks by vacancy ID and employee ID."
    )
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByVacancyIdAndEmployeeId(
            @PathVariable("vacancyId")
            @Parameter(description = "ID of the vacancy", example = "1")
            Integer vacancyId,
            @PathVariable("employeeId")
            @Parameter(description = "ID of the employee", example = "1")
            Integer employeeId) {
        return new ResponseEntity<>(service.findAllByVacancyIdAndEmployeeId(vacancyId, employeeId), HttpStatus.OK);
    }
}
