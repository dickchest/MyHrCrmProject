package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import com.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import com.myhrcrmproject.service.CompensationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/compensations")
@AllArgsConstructor
@Getter
public class CompensationController {
    private final CompensationService service;

    @GetMapping
    public ResponseEntity<List<CompensationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompensationResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompensationResponseDTO> createNew(@RequestBody CompensationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompensationResponseDTO> update(@PathVariable("id") Integer id, @RequestBody CompensationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @IsManager
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByContract/{id}")
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByContractId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByContractId(id), HttpStatus.OK);
    }

}
