package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import crm.myhrcrmproject.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/clients")
@AllArgsConstructor
@Getter
public class ClientController extends GenericController<ClientRequestDTO, ClientResponseDTO> {
    private final ClientService service;
}
