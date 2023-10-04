package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.*;
import crm.myhrcrmproject.dto.contactDetailsDTO.ContactDetailsDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.RoleRepository;
import crm.myhrcrmproject.repository.UserDetailsRepository;
import crm.myhrcrmproject.service.utills.UserDetailsConverter;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsService implements CommonService<UserDetailsRequestDTO, UserDetailsResponseDTO> {
    private final UserDetailsRepository repository;
    private final UserDetailsConverter converter;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;


    @Override
    public List<UserDetailsResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsResponseDTO findById(Integer id) {
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    @Override
    public UserDetailsResponseDTO create(UserDetailsRequestDTO requestDTO) {
        UserDetails entity = converter.fromDTO(converter.newEntity(), requestDTO);

        // обновляем даты
        entity.setCreatedDate(LocalDateTime.now());
        entity.setUpdatedDate(LocalDateTime.now());

        // устанавливаем роль по умолчанию
        Role role = roleRepository.findByName("user").get();
        entity.setRole(role);

        // создаем employee, который будет привязан к user
        // и устанавливаем емейл
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO();
        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setEmail(requestDTO.getEmail());
        employeeRequestDTO.setContactDetails(contactDetailsDTO);

        EmployeeResponseDTO employeeResponseDTO = employeeService.create(employeeRequestDTO);
        Employee employee = employeeRepository.findById(employeeResponseDTO.getId()).get();

        // связываем их
        entity.setEmployee(employee);
        return converter.toDTO(repository.save(entity));
    }

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

    @Override
    public void delete(Integer id) {
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found!"));
        repository.delete(entity);
    }

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
}
