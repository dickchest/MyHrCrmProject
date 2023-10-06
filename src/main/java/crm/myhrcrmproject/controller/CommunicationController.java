package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.communicationDTO.CommunicationRequestDTO;
import crm.myhrcrmproject.dto.communicationDTO.CommunicationResponseDTO;
import crm.myhrcrmproject.service.CommunicationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/communications")
@AllArgsConstructor
@Getter
public class CommunicationController extends GenericController<CommunicationRequestDTO, CommunicationResponseDTO> {
    private final CommunicationService service;

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
