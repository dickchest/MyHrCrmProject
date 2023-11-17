package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Client;
import com.myhrcrmproject.dto.clientDTO.ClientRequestDTO;
import com.myhrcrmproject.dto.clientDTO.ClientResponseDTO;
import com.myhrcrmproject.repository.ClientRepository;
import com.myhrcrmproject.service.utills.CandidateConverter;
import com.myhrcrmproject.service.utills.ClientConverter;
import com.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ClientService implements CommonService<ClientRequestDTO, ClientResponseDTO> {
    private final ClientRepository repository;
    private final ClientConverter converter;

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
