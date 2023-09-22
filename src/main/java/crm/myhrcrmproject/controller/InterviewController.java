package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.interviewDTO.InterviewDateRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewRequestDTO;
import crm.myhrcrmproject.dto.interviewDTO.InterviewResponseDTO;
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
public class InterviewController extends GenericController<InterviewRequestDTO, InterviewResponseDTO> {
    private final InterviewService service;

    @GetMapping("/findAllByStatus/{id}")
    public ResponseEntity<List<InterviewResponseDTO>> findAllByStatus(@PathVariable("id") Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatusId(statusId), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<InterviewResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @PutMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<InterviewResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id, @RequestBody InterviewDateRequestDTO requestDTO) {
        return new ResponseEntity<>(service.findAllByDateAndEmployeeId(requestDTO, id), HttpStatus.OK);
    }
}
