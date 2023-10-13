package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.domain.annotations.IsAdministrator;
import crm.myhrcrmproject.domain.annotations.IsManager;
import crm.myhrcrmproject.domain.annotations.IsUser;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
@Getter
public class EmployeeController {
    private final EmployeeService service;

    @IsUser
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @IsUser
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @IsAdministrator
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createNew(@RequestBody EmployeeRequestDTO requestDTO) {
        return new ResponseEntity<>(service.create(requestDTO), HttpStatus.CREATED);
    }

    @IsUser
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(@PathVariable("id") Integer id, @RequestBody EmployeeRequestDTO requestDTO) {
        return new ResponseEntity<>(service.update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @IsManager
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        service.delete(id);
    }

}
