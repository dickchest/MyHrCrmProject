package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.service.UserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
public class UserDetailsController extends GenericController<UserDetailsRequestDTO, UserDetailsResponseDTO>{
    private final UserDetailsService service;

}
