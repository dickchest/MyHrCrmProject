package com.myhrcrmproject.controller;

import com.myhrcrmproject.domain.annotations.IsManager;
import com.myhrcrmproject.dto.vacancyDTO.VacancyRequestDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyResponseDTO;
import com.myhrcrmproject.dto.vacancyDTO.VacancyShortResponseDTO;
import com.myhrcrmproject.service.VacancyService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vacancies")
@AllArgsConstructor
@Getter
public class VacancyController {
    private final VacancyService service;


    @GetMapping
    public ResponseEntity<List<VacancyResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @IsManager
    @PostMapping
    public ResponseEntity<VacancyResponseDTO> createNew(@RequestBody VacancyRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @IsManager
    @PutMapping("/{id}")
    public ResponseEntity<VacancyResponseDTO> update(@PathVariable("id") Integer id, @RequestBody VacancyRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }


    @GetMapping("/findAllByStatus/{id}")
    public ResponseEntity<List<VacancyResponseDTO>> findAllByStatus(@PathVariable("id") Integer statusId) {
        return new ResponseEntity<>(service.findAllByStatusId(statusId), HttpStatus.OK);
    }

    @GetMapping("/findAllByEmployee/{id}")
    public ResponseEntity<List<VacancyShortResponseDTO>> findAllByEmployeeId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByEmployeeId(id), HttpStatus.OK);
    }
}
