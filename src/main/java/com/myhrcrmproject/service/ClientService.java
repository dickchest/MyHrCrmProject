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
/**
 * Service class for managing client-related operations in the HR CRM system.
 *
 * <p>This service provides functionality for creating, updating, retrieving, and deleting client records.
 * It interacts with the {@code ClientRepository} for data access and the {@code ClientConverter} for
 * converting between entity and DTO objects.
 *
 * <p>The class is annotated with {@code @Service} to indicate its role as a Spring service bean.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ClientService implements CommonService<ClientRequestDTO, ClientResponseDTO> {
    private final ClientRepository repository;
    private final ClientConverter converter;

    /**
     * Retrieves a list of all clients in the system.
     *
     * @return A list of {@code ClientResponseDTO} representing all clients.
     */
    @Override
    public List<ClientResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a client by their unique identifier.
     *
     * @param id The identifier of the client to retrieve.
     * @return The {@code ClientResponseDTO} representing the retrieved client.
     * @throws NotFoundException if the client with the specified ID is not found.
     */
    @Override
    public ClientResponseDTO findById(Integer id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " not found!"));

        return converter.toDTO(entity);
    }

    /**
     * Creates a new client record based on the provided data.
     *
     * @param requestDTO The {@code ClientRequestDTO} containing data for the new client.
     * @return The {@code ClientResponseDTO} representing the newly created client.
     */
    @Override
    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        Client entity = converter.fromDTO(converter.newEntity(), requestDTO);

        return converter.toDTO(repository.save(entity));
    }

    /**
     * Updates an existing client record with new data.
     *
     * @param id         The identifier of the client to update.
     * @param requestDTO The {@code ClientRequestDTO} containing updated data for the client.
     * @return The {@code ClientResponseDTO} representing the updated client.
     * @throws NotFoundException if the client with the specified ID is not found.
     */
    @Override
    public ClientResponseDTO update(Integer id, ClientRequestDTO requestDTO) {
        Client existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a client by their unique identifier.
     *
     * @param id The identifier of the client to delete.
     * @throws NotFoundException if the client with the specified ID is not found.
     */
    @Override
    public void delete(Integer id) {
        Client entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id: " + id + " not found!"));
        repository.delete(entity);
    }
}
