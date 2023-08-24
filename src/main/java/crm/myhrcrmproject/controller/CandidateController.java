package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.service.CandidateService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("api/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidatesResponseDTO>> findAllCandidates() {
        return new ResponseEntity<>(candidateService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/findAllByStatus/{status}")
    public ResponseEntity<List<CandidatesResponseDTO>> findAllTasks (@PathVariable("status") Integer statusID){
        CandidateStatus status = CandidateStatus.values()[statusID];
        return new ResponseEntity<>(candidateService.findAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatesResponseDTO> findCandidateById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(candidateService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidatesResponseDTO> createNewCandidate(@RequestBody CandidatesRequestDTO requestDTO) {
        return new ResponseEntity<>(candidateService.createCandidate(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatesResponseDTO> updateCandidate(@PathVariable("id") Integer id, @RequestBody CandidatesRequestDTO requestDTO) {
        return new ResponseEntity<>(candidateService.updateCandidate(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id){
        candidateService.deleteCandidate(id);
    }

}
