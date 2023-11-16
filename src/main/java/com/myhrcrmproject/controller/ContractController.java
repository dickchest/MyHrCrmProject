package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import com.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contracts")
@AllArgsConstructor
@Getter
public class ContractController {
    private final ContractService service;

    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContractResponseDTO> createNew(@RequestBody ContractRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponseDTO> update(@PathVariable("id") Integer id, @RequestBody ContractRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

    @GetMapping("/findAllByContractTypeId/{id}")
    public ResponseEntity<List<ContractResponseDTO>> findAllByContractTypeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByContractTypeId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<ContractResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByClient/{id}")
    public ResponseEntity<List<ContractResponseDTO>> findAllByClientId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByClientId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<ContractResponseDTO>> findAllActiveContracts(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllActiveContracts")
    public ResponseEntity<List<ContractResponseDTO>> findAllActiveContracts() {
        return new ResponseEntity<>(service.findAllActiveContracts(), HttpStatus.OK);
    }
}
