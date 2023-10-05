package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Client;
import crm.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientShortResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientConverter {

    public ClientResponseDTO toDTO(Client entity) {
        return ClientResponseDTO.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .description(entity.getDescription())
                .build();
    }

    public Client fromDTO(Client entity, ClientRequestDTO request) {
        Optional.ofNullable(request.getCompanyName()).ifPresent(entity::setCompanyName);
        Optional.ofNullable(request.getDescription()).ifPresent(entity::setDescription);
        return entity;
    }

    public Client newEntity() {
        return new Client();
    }

    public ClientShortResponseDTO toShortDTO(Client entity) {
        return ClientShortResponseDTO.builder()
                .id(entity.getId())
                .companyName(entity.getCompanyName())
                .build();
    }
}
