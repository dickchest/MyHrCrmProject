package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import crm.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import crm.myhrcrmproject.service.CandidateService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/candidates")
@AllArgsConstructor
@Getter
public class CandidateController extends GenericController<Candidate, CandidateRequestDTO, CandidateResponseDTO> {
    private final CandidateService service;

    @GetMapping("/findAllByStatus/{id}")
    public ResponseEntity<List<CandidateResponseDTO>> findAllByStatusId(@PathVariable("id") Integer statusID) {
        return new ResponseEntity<>(service.findAllByStatusId(statusID), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancy/{id}")
    public ResponseEntity<List<CandidateResponseDTO>> findAllByVacancyId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }
}
