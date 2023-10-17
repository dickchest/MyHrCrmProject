package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import crm.myhrcrmproject.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
@Slf4j
public class UserDetailsController {
    private final UserDetailsServiceImpl service;

    @GetMapping
    public ResponseEntity<List<UserDetailsResponseDTO>> findAll() {
        return new ResponseEntity<>(getService().findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(getService().findById(id), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<UserDetailsResponseDTO> createNew(@RequestBody UserDetailsRequestDTO requestDTO) {
//        return new ResponseEntity<>(getService().create(requestDTO), HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDTO> update(@PathVariable("id") Integer id, @RequestBody UserDetailsRequestDTO requestDTO) {
        return new ResponseEntity<>(getService().update(id, requestDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidate(@PathVariable("id") Integer id) {
        service.delete(id);
    }

    @PutMapping("/setRole") // запрос: api/users/setRole?id=2&role=manager
    public ResponseEntity<UserDetailsShortResponseDTO> setRole(@RequestParam("id") Integer id, @RequestParam("role") String request) {
        log.info("Set Role controller has been called");
        return new ResponseEntity<>(service.setRole(id, request), HttpStatus.ACCEPTED);
    }

}
