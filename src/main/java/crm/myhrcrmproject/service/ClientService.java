package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Client;
import crm.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import crm.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import crm.myhrcrmproject.repository.ClientRepository;
import crm.myhrcrmproject.service.utills.CandidateConverter;
import crm.myhrcrmproject.service.utills.ClientConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ClientService implements CommonService<ClientRequestDTO, ClientResponseDTO> {
    private final ClientRepository repository;
    private final ClientConverter converter;
    private final CandidateConverter candidateConverter;

    @Override
    public List<ClientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponseDTO findById(Integer id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " not found!"));

        return converter.toDTO(entity);
    }

    @Override
    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        Client entity = converter.fromDTO(converter.newEntity(), requestDTO);

        return converter.toDTO(repository.save(entity));
    }

    @Override
    public ClientResponseDTO update(Integer id, ClientRequestDTO requestDTO) {
        Client existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    @Override
    public void delete(Integer id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found!"));
        repository.delete(entity);
    }
}
