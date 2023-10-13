package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.annotations.IsManager;
import crm.myhrcrmproject.domain.annotations.IsUser;
import crm.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewShortResponseDTO;
import crm.myhrcrmproject.service.InterviewService;
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
public class InterviewController{
    private final InterviewService service;

    @IsManager
    @GetMapping
    public ResponseEntity<List<InterviewResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InterviewResponseDTO> createNew(@RequestBody InterviewRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponseDTO> update(@PathVariable("id") Integer id, @RequestBody InterviewRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

    @GetMapping("/findAllByStatus/{id}")
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByStatusId(@PathVariable("id") Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatusId(statusId), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @PutMapping("/findAllByDateAndEmployee/{id}")
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id, @RequestBody InterviewDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDateAndEmployeeId(requestDTO, id), HttpStatus.OK);
    }

    @PutMapping("/findAllByDate")
    public ResponseEntity<List<InterviewShortResponseDTO>> findAllByDate(@RequestBody InterviewDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDate(requestDTO), HttpStatus.OK);
    }
}
