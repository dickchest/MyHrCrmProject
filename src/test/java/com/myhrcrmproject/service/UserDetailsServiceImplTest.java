package com.myhrcrmproject.service;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Role;
import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.dto.employeeDTO.EmployeeRequestDTO;
import com.myhrcrmproject.dto.employeeDTO.EmployeeResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.repository.ContactDetailsRepository;
import com.myhrcrmproject.repository.EmployeeRepository;
import com.myhrcrmproject.repository.RoleRepository;
import com.myhrcrmproject.repository.UserDetailsRepository;
import com.myhrcrmproject.service.utills.UserDetailsConverter;
import com.myhrcrmproject.service.validation.AlreadyExistsException;
import com.myhrcrmproject.service.validation.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Mock
    private ContactDetailsRepository contactDetailsRepository;


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
    void testFindById_success() {
        // given
        UserDetails user = new UserDetails();
        user.setId(1);
        user.setUserName("testName");

        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        responseDTO.setId(1);
        responseDTO.setUserName("testName");

        // when
        when(userDetailsRepository.findById(1)).thenReturn(Optional.of(user));
        when(userDetailsConverter.toDTO(user)).thenReturn(responseDTO);

        // when: perform the actual test
        UserDetailsResponseDTO result = userDetailsService.findById(1);

        // then
        verify(userDetailsRepository, times(1)).findById(anyInt());
        assertEquals(responseDTO, result);
    }

    @Test
    void testFindById_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock repository to return an empty optional when findById is called
        when(userDetailsRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userDetailsService.findById(id));
        assertEquals("User with id: " + id + " not found!", exception.getMessage());

        // Verify
        verify(userDetailsRepository, never()).delete(any(UserDetails.class));
    }

    @Test
    void testCreate_successful() {
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
        when(contactDetailsRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userDetailsService.create(userDetailsRequestDTO);
        verify(userDetailsRepository, times(1)).save(any(UserDetails.class));
    }

    @Test
    void testCreate_throwAlreadyExistsException() {
        // given
        UserDetailsRequestDTO userDetailsRequestDTO = new UserDetailsRequestDTO();
        userDetailsRequestDTO.setUserName("testName");
        userDetailsRequestDTO.setPassword("testPassword");

        // when
        when(userDetailsRepository.findByUserName(userDetailsRequestDTO.getUserName())).thenReturn(Optional.of(new UserDetails()));

        // Act and assert
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> userDetailsService.create(userDetailsRequestDTO));
        assertEquals("User with name " + userDetailsRequestDTO.getUserName() + " already exists", exception.getMessage());

        // then
        verify(userDetailsRepository, never()).save(any(UserDetails.class));
    }

    @Test
    void testUpdate_throwNotFoundException() {
        // Arrange
        int id = 1;
        UserDetailsRequestDTO requestDTO = new UserDetailsRequestDTO();
        requestDTO.setUserName("UpdatedTestName");

        // Mock repository to return an empty optional when findById is called
        when(userDetailsRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userDetailsService.update(id, requestDTO));
        assertEquals("User with id: " + id + " not found!", exception.getMessage());

        // Verify
        verify(userDetailsRepository, never()).delete(any(UserDetails.class));
    }

    @Test
    void testDelete_successful() {
        when(userDetailsRepository.findById(anyInt())).thenReturn(Optional.of(new UserDetails()));
        userDetailsService.delete(1);
        verify(userDetailsRepository, times(1)).delete(any(UserDetails.class));
    }

    @Test
    void testDeleteCandidate_throwNotFoundException() {
        // Arrange
        int id = 1;

        // Mock repository to return an empty optional when findById is called
        when(userDetailsRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userDetailsService.delete(id));
        assertEquals("User with id: " + id + " not found!", exception.getMessage());

        // Verify
        verify(userDetailsRepository, never()).delete(any(UserDetails.class));
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
}