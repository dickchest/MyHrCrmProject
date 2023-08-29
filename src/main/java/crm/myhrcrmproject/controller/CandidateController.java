package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Vacancy;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import crm.myhrcrmproject.domain.enums.VacancyStatus;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.dto.candidatesDTO.CandidatesResponseDTO;
import crm.myhrcrmproject.dto.vacanciesDTO.VacanciesResponseDTO;
import crm.myhrcrmproject.service.CandidateService;
import crm.myhrcrmproject.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/candidates")
public class CandidateController extends GenericController<Candidate, CandidatesRequestDTO, CandidatesResponseDTO> {
    private final CandidateService candidateService;

    protected CandidateController(GenericService<Candidate, CandidatesRequestDTO, CandidatesResponseDTO> service, CandidateService candidateService) {
        super(service);
        this.candidateService = candidateService;
    }

    @GetMapping("/findAllByStatus/{statusId}")
    public ResponseEntity<List<CandidatesResponseDTO>> findAllByStatus(@PathVariable("statusId") Integer statusID) {
        return new ResponseEntity<>(candidateService.findAllByStatus(statusID), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancy/{vacancyId}")
    public ResponseEntity<List<CandidatesResponseDTO>> findAllByVacancyId(@PathVariable("vacancyId") Integer id) {
        return new ResponseEntity<>(candidateService.findAllByVacancyId(id), HttpStatus.OK);
    }
}
