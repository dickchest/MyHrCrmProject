package com.myhrcrmproject.service;

import java.util.List;
/**
 * Generic interface for common CRUD (Create, Read, Update, Delete) operations in a service layer.
 *
 * <p>This interface defines standard methods for performing CRUD operations on entities. Implementing
 * services should provide concrete implementations for these methods based on the specific requirements
 * of the entities they manage.
 *
 * <p>The type parameters {@code Q} and {@code R} represent the request and response DTO types, respectively.
 *
 * @param <Q> The type of the request DTO.
 * @param <R> The type of the response DTO.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
public interface CommonService<Q, R> {

    /**
     * Retrieves a list of all entities.
     *
     * @return A list of response DTOs representing all entities.
     */
    List<R> findAll();

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The identifier of the entity to retrieve.
     * @return The response DTO representing the retrieved entity.
     */
    R findById(Integer id);

    /**
     * Creates a new entity based on the provided data.
     *
     * @param requestDTO The request DTO containing data for the new entity.
     * @return The response DTO representing the newly created entity.
     */
    R create(Q requestDTO);

    /**
     * Updates an existing entity with new data.
     *
     * @param id         The identifier of the entity to update.
     * @param requestDTO The request DTO containing updated data for the entity.
     * @return The response DTO representing the updated entity.
     */
    R update(Integer id, Q requestDTO);

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The identifier of the entity to delete.
     */
    void delete(Integer id);
}
