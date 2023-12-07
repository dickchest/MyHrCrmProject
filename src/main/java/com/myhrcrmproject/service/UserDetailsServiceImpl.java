package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Role;
import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.RoleRepository;
import com.myhrcrmproject.repository.UserDetailsRepository;
import com.myhrcrmproject.service.utills.UserDetailsConverter;
import com.myhrcrmproject.service.validation.AlreadyExistsException;
import com.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that handles CRUD (Create, Read, Update, Delete) operations for user details entities.
 *
 * <p>This service provides methods to perform operations on user details entities, such as retrieving,
 * creating, updating, and deleting user details records. It also includes additional methods for setting
 * user roles and implementing Spring Security's {@code UserDetailsService}.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements CommonService<UserDetailsRequestDTO, UserDetailsResponseDTO>, org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDetailsRepository repository;
    private final UserDetailsConverter converter;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);

    /**
     * Retrieves a list of all user details records.
     *
     * @return A list of response DTOs representing all user details records.
     */
    @Override
    public List<UserDetailsResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user details record by its unique identifier.
     *
     * @param id The identifier of the user details record to retrieve.
     * @return The response DTO representing the retrieved user details record.
     * @throws NotFoundException if the user details record with the specified id is not found.
     */
    @Override
    public UserDetailsResponseDTO findById(Integer id) {
//        LOGGER.log(Level.INFO, String.format("Вызван метод findById с параметром %d", id));
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));
        return converter.toDTO(entity);
    }


    /**
     * Creates a new user details record based on the provided request DTO.
     *
     * @param requestDTO The request DTO containing information for creating the user details record.
     * @return The response DTO representing the created user details record.
     * @throws AlreadyExistsException if a user with the specified username already exists.
     */
    @Override
    public UserDetailsResponseDTO create(UserDetailsRequestDTO requestDTO) {

        if (repository.findByUserName(requestDTO.getUserName()).isEmpty()) {
            // todo добавить проверку, что такой емейл существует

            UserDetails entity = converter.fromDTO(converter.newEntity(), requestDTO);


            // обновляем даты
            entity.setCreatedDate(LocalDateTime.now());
            entity.setUpdatedDate(LocalDateTime.now());

            // устанавливаем роль по умолчанию
            Role role = roleRepository.findByName("user").get();
            entity.setRole(role);

            // создаем employee, который будет привязан к user
            // и устанавливаем mail
            EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
            ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
            contactDetailsDTO.setEmail(requestDTO.getEmail());
            employeeRequestDTO.setContactDetails(contactDetailsDTO);

            EmployeeResponseDTO employeeResponseDTO = employeeService.create(employeeRequestDTO);
            Employee employee = employeeRepository.findById(employeeResponseDTO.getId()).get();

            // связываем их
            entity.setEmployee(employee);
            return converter.toDTO(repository.save(entity));
        } else {
            throw new AlreadyExistsException("User with name " + requestDTO.getUserName() + " already exists");
        }
    }

    /**
     * Updates an existing user details record based on the provided id and request DTO.
     *
     * @param id         The identifier of the user details record to update.
     * @param requestDTO The request DTO containing information for updating the user details record.
     * @return The response DTO representing the updated user details record.
     * @throws NotFoundException if the user details record with the specified id is not found.
     */
    @Override
    public UserDetailsResponseDTO update(Integer id, UserDetailsRequestDTO requestDTO) {
        UserDetails existingEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));

        // filled in existing fields with new dates
        converter.fromDTO(existingEntity, requestDTO);
        existingEntity.setUpdatedDate(LocalDateTime.now());
        repository.save(existingEntity);

        return converter.toDTO(existingEntity);
    }

    /**
     * Deletes a user details record based on the provided id.
     *
     * @param id The identifier of the user details record to delete.
     * @throws NotFoundException if the user details record with the specified id is not found.
     */
    @Override
    public void delete(Integer id) {
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));
        repository.delete(entity);
    }

    /**
     * Sets the role of a user based on the provided id and role name.
     *
     * @param id       The identifier of the user details record to update.
     * @param userRole The name of the role to set.
     * @return The short response DTO representing the user details record with the updated role.
     * @throws NotFoundException if the user details record with the specified id or the specified role is not found.
     */
    public UserDetailsShortResponseDTO setRole(Integer id, String userRole) {
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        System.out.println(userRole);
        Role role = roleRepository.findByName(userRole)
                .orElseThrow(() -> new NotFoundException("Role " + userRole + " not found!"));
        entity.setRole(role);
        entity.setUpdatedDate(LocalDateTime.now());
        repository.save(entity);
        return converter.toShortDTO(entity);
    }

    /**
     * Loads a user by the given username.
     *
     * @param userName The username of the user to load.
     * @return The UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException if the user with the specified username is not found.
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails entity = repository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User with name: " + userName + " not found!"));
        return entity;
    }
}
