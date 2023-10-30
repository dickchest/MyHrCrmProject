package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import com.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import com.myhrcrmproject.service.CommunicationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/communications")
@AllArgsConstructor
@Getter
public class CommunicationController {
    private final CommunicationService service;

    @GetMapping
    public ResponseEntity<List<CommunicationResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunicationResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommunicationResponseDTO> createNew(@RequestBody CommunicationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunicationResponseDTO> update(@PathVariable("id") Integer id, @RequestBody CommunicationRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

    @GetMapping("/findAllByCommunicationTypeId/{id}")
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByCommunicationTypeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCommunicationTypeId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByClient/{id}")
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByClientId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByClientId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByVacancy/{id}")
    public ResponseEntity<List<CommunicationResponseDTO>> findAllByVacancyId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByVacancyId(id), HttpStatus.OK);
    }
}
