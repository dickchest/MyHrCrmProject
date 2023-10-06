package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.taskDTO.TaskDateRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskRequestDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskResponseDTO;
import crm.myhrcrmproject.dto.taskDTO.TaskShortResponseDTO;
import crm.myhrcrmproject.service.TaskService;
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
public class TaskController extends GenericController<TaskRequestDTO, TaskResponseDTO> {
    private final TaskService service;

    @GetMapping("/findAllByTaskStatusId/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByTaskStatusId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByTaskStatusId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancy/{id}")
    public ResponseEntity<List<TaskResponseDTO>> findAllByVacancyId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }

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
