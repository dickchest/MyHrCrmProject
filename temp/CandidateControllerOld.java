package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.dto.candidateDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.service.CandidateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RequestMapping("api/candidates")
public class CandidateControllerOld {
    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidatesResponseDTO>> findAll() {
        return new ResponseEntity<>(candidateService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/findAllByStatus/{status}")
    public ResponseEntity<List<CandidatesResponseDTO>> findAllByStatus (@PathVariable("status") Integer statusID){
        CandidateStatus status = CandidateStatus.values()[statusID];
        return new ResponseEntity<>(candidateService.findAllByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatesResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(candidateService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidatesResponseDTO> createNew(@RequestBody CandidatesRequestDTO requestDTO) {
        return new ResponseEntity<>(candidateService.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatesResponseDTO> update(@PathVariable("id") Integer id, @RequestBody CandidatesRequestDTO requestDTO) {
        return new ResponseEntity<>(candidateService.update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id){
        candidateService.delete(id);
    }

}
