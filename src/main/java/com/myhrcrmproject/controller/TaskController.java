package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.domain.annotations.IsUser;
import com.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import com.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import com.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import com.myhrcrmproject.service.TaskService;
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
public class TaskController {
    private final TaskService service;

    @IsManager
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createNew(@RequestBody TaskRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable("id") Integer id, @RequestBody TaskRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

    @IsManager
    @GetMapping("/findAllByTaskStatusId/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByTaskStatusId(@PathVariable("id") Integer id) {
        System.out.println("received by status id request");
        return new ResponseEntity<>(service.findAllByTaskStatusId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @IsManager
    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @IsManager
    @GetMapping("/findAllByVacancy/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByVacancyId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }

    @IsManager
    @PutMapping("/findAllByStartDate")
    public ResponseEntity<List<TaskResponseDTO>> findAllByStartDate(@RequestBody TaskDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByStartDate(requestDTO), HttpStatus.OK);
    }

    @PutMapping("/findAllByStartDateAndEmployee/{id}")
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByStartDateAndEmployeeId(@PathVariable("id") Integer id, @RequestBody TaskDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDateAndEmployeeId(id, requestDTO), HttpStatus.OK);
    }

    @GetMapping("/findAllByCandidateAndEmployee/{candidateId}/{employeeId}")
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByCandidateIdAndEmployeeId(@PathVariable("candidateId") Integer candidateId, @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(service.findAllByCandidateIdAndEmployeeId(candidateId, employeeId), HttpStatus.OK);
    }

    @GetMapping("/findAllByStatusAndEmployee/{statusId}/{employeeId}")
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByStatusIdAndEmployeeId(@PathVariable("statusId") Integer statusId, @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(service.findAllByStatusIdAndEmployeeId(statusId, employeeId), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancyAndEmployee/{vacancyId}/{employeeId}")
    public ResponseEntity<List<TaskShortResponseDTO>> findAllByVacancyIdAndEmployeeId(@PathVariable("vacancyId") Integer vacancyId, @PathVariable("employeeId") Integer employeeId) {
        return new ResponseEntity<>(service.findAllByVacancyIdAndEmployeeId(vacancyId, employeeId), HttpStatus.OK);
    }
}
