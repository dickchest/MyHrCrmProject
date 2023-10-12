package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import crm.myhrcrmproject.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
public class UserDetailsController extends GenericController<UserDetailsRequestDTO, UserDetailsResponseDTO>{
    private final UserDetailsServiceImpl service;

    @PutMapping("/setRole") // запрос: api/users/setRole?id=2&role=manager
    public ResponseEntity<UserDetailsShortResponseDTO> setRole(@RequestParam("id") Integer id, @RequestParam("role") String request) {
        return new ResponseEntity<>(service.setRole(id, request), HttpStatus.ACCEPTED);
    }

}
