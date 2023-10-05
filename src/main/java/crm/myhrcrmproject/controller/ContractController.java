package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.contractDTO.ContractRequestDTO;
import crm.myhrcrmproject.dto.contractDTO.ContractResponseDTO;
import crm.myhrcrmproject.service.ContractService;
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
public class ContractController extends GenericController<ContractRequestDTO, ContractResponseDTO> {
    private final ContractService service;

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
    public ResponseEntity<List<ContractResponseDTO>> findAllByCandidateId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findAllByCandidateId(id), HttpStatus.OK);
    }

    @GetMapping("/findAllActiveContracts")
    public ResponseEntity<List<ContractResponseDTO>> findAllByCandidateId() {
        return new ResponseEntity<>(service.findAllActiveContracts(), HttpStatus.OK);
    }
}
