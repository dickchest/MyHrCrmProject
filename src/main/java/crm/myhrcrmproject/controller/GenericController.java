package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericController<T, RequestDTO, ResponseDTO> {

    protected final GenericService<T, RequestDTO, ResponseDTO> service;

    protected GenericController(GenericService<T, RequestDTO, ResponseDTO> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createNew(@RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(service.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable("id") Integer id, @RequestBody RequestDTO requestDTO) {
        return new ResponseEntity<>(service.update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id){
        service.delete(id);
    }

}
