package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.compensationDTO.CompensationRequestDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationResponseDTO;
import crm.myhrcrmproject.dto.compensationDTO.CompensationShortResponseDTO;
import crm.myhrcrmproject.service.CompensationService;
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
public class CompensationController extends GenericController<CompensationRequestDTO, CompensationResponseDTO> {
    private final CompensationService service;

    @GetMapping("/findAllByCandidate/{id}")
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllByContract/{id}")
    public ResponseEntity<List<CompensationShortResponseDTO>> findAllByContractId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByContractId(id), HttpStatus.OK);
    }
    
}
