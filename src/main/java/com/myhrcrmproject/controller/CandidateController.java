package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.candidateDTO.CandidateRequestDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateResponseDTO;
import com.myhrcrmproject.dto.candidateDTO.CandidateShortResponseDTO;
import com.myhrcrmproject.service.CandidateService;
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
public class CandidateController {
    private final CandidateService service;

    @GetMapping
    public ResponseEntity<List<CandidateResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidateResponseDTO> createNew(@RequestBody CandidateRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> update(@PathVariable("id") Integer id, @RequestBody CandidateRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }


    @GetMapping("/findAllByStatus/{id}")
    public ResponseEntity<List<CandidateShortResponseDTO>> findAllByStatusId(@PathVariable("id") Integer statusID) {
        return new ResponseEntity<>(service.findAllByStatusId(statusID), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancy/{id}")
    public ResponseEntity<List<CandidateShortResponseDTO>> findAllByVacancyId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }
}
