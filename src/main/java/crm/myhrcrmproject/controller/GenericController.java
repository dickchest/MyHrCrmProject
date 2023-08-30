package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.candidatesDTO.CandidatesRequestDTO;
import crm.myhrcrmproject.service.CommonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T, RequestDTO, ResponseDTO> {

    protected abstract CommonService<T, RequestDTO, ResponseDTO> getService();


    @GetMapping
    public ResponseEntity<List<ResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createNew(@RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable("id") Integer id, @RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        getService().delete(id);
    }

}
