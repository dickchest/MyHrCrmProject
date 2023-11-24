package com.myhrcrmproject.service.auth;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.repository.UserDetailsRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityHelperTest {
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @InjectMocks
    private SecurityHelper securityHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsAuthUserEqualsEmployee_Admin() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee = new Employee();
        employee.setFirstName("TestName");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("UserName");
        userDetails.setEmployee(employee);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("admin");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_ADMIN"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAuthUserEqualsEmployee_Manager() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee = new Employee();
        employee.setFirstName("TestName");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("UserName");
        userDetails.setEmployee(employee);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("admin");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_MANAGER"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAuthUserEqualsEmployee_User() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee = new Employee();
        employee.setFirstName("TestName");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("UserName");
        userDetails.setEmployee(employee);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("UserName");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_USER"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAuthUserEqualsEmployee_IsNull() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee = new Employee();
        employee.setFirstName("TestName");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("UserName");

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("UserName");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_USER"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsAuthUserEqualsEmployee_UserDoesNotMatch() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee1 = new Employee();
        employee1.setFirstName("TestName1");

        Employee employee2 = new Employee();
        employee2.setFirstName("TestName2");

        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("UserName");
        userDetails.setEmployee(employee1);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("UserName");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_USER"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee2);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsAuthUserEqualsEmployee_noMatch() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        Employee employee = new Employee();
        employee.setFirstName("TestName");

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn("admin");

        Collection grantedAuthorities =
                Lists.newArrayList(
                        new SimpleGrantedAuthority("ROLE_USER"));

        when(authentication.getAuthorities()).thenReturn(grantedAuthorities);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        boolean result = securityHelper.isAuthUserEqualsEmployee(employee);

        // Assert
        assertFalse(result);
    }


    @Test
    void testGetCurrentAuthEmployee() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserDetails userDetails = new UserDetails();

        Employee employee = new Employee();
        employee.setFirstName("testEmployee");

        userDetails.setEmployee(employee);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.of(userDetails));
        when(authentication.getName()).thenReturn("testEmployee");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Optional<Employee> result = securityHelper.getCurrentAuthEmployee();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(employee, result.get());
    }

    @Test
    void testGetCurrentAuthEmployee_NoEmployee() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(userDetailsRepository.findByUserName(anyString())).thenReturn(Optional.empty());
        when(authentication.getName()).thenReturn("testEmployee");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        Optional<Employee> result = securityHelper.getCurrentAuthEmployee();

        // Assert
        assertFalse(result.isPresent());
    }
}