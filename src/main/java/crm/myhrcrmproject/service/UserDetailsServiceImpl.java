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
import crm.myhrcrmproject.service.validation.AlreadyExistsException;
import crm.myhrcrmproject.service.validation.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements CommonService<UserDetailsRequestDTO, UserDetailsResponseDTO>, org.springframework.security.core.userdetails.UserDetailsService {
    private final UserDetailsRepository repository;
    private final UserDetailsConverter converter;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);


    @Override
    public List<UserDetailsResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsResponseDTO findById(Integer id) {
//        LOGGER.log(Level.INFO, String.format("Вызван метод findById с параметром %d", id));
        UserDetails entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return converter.toDTO(entity);
    }

    @Override
    public UserDetailsResponseDTO create(UserDetailsRequestDTO requestDTO) {
//        LOGGER.info("Вызван метод create с параметром %d");

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

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails entity = repository.findByUserName(userName)
                .orElseThrow(()-> new NotFoundException("User with name: " + userName + " not found!"));
        return entity;
    }
}
