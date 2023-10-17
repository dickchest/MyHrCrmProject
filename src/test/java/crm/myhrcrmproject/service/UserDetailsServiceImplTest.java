package crm.myhrcrmproject.service;

import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.Role;
import crm.myhrcrmproject.domain.UserDetails;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import crm.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.repository.EmployeeRepository;
import crm.myhrcrmproject.repository.RoleRepository;
import crm.myhrcrmproject.repository.UserDetailsRepository;
import crm.myhrcrmproject.service.utills.UserDetailsConverter;
import crm.myhrcrmproject.service.validation.AlreadyExistsException;
import crm.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {


    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDetailsConverter userDetailsConverter;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeResponseDTO employeeResponseDTO;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindAll() {
        userDetailsService.findAll();
        verify(userDetailsRepository, times(1)).findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void testCreate() {
        UserDetailsRequestDTO userDetailsRequestDTO = new UserDetailsRequestDTO();
        userDetailsRequestDTO.setUserName("testName");
        userDetailsRequestDTO.setPassword("testPassword");
        when(userDetailsConverter.fromDTO(any(), eq(userDetailsRequestDTO))).thenReturn(new UserDetails());
        when(roleRepository.findByName("user")).thenReturn(Optional.of(new Role(3, "user")));
        when(employeeResponseDTO.getId()).thenReturn(1);
        EmployeeResponseDTO employeeResponseDTO = EmployeeResponseDTO.builder()
                .id(1)
                .firstName("test")
                .lastName("testws")
                .build();

        when(employeeService.create(any(EmployeeRequestDTO.class))).thenReturn(employeeResponseDTO);

        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new Employee()));

        userDetailsService.create(userDetailsRequestDTO);
        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
        when(userDetailsRepository.findById(anyInt())).thenReturn(Optional.of(new UserDetails()));
        userDetailsService.delete(1);
        verify(userDetailsRepository, times(1)).delete(any(UserDetails.class));
    }

    @Test
    void setRole() {
    }

    @Test
    void testLoadUserByUsername() {
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(new UserDetails()));
        userDetailsService.loadUserByUsername("testName");
        verify(userDetailsRepository, times(1)).findByUserName(anyString());
    }

    @Test
    void testFindByUserNameUserNotFound() {
        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("name");
        });
    }

    @Test
    void testCreateUserAlreadyExists() {
        UserDetailsRequestDTO userDetailsRequestDTO = new UserDetailsRequestDTO();
        userDetailsRequestDTO.setUserName("existingUser");
        when(userDetailsRepository.findByUserName(userDetailsRequestDTO.getUserName())).thenReturn(Optional.of(new UserDetails()));
        assertThrows(AlreadyExistsException.class, () -> {
            userDetailsService.create(userDetailsRequestDTO);
        });
    }

    //testCreateUserRoleNotFound
    //testDeleteUserNotFound

}