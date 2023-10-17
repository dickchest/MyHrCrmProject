package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.service.UserDetailsServiceImpl;
import crm.myhrcrmproject.service.auth.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserDetailsController.class)
class UserDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl service;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFilterForValidToken() throws Exception {
        // given
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        System.out.println("");

        UserDetails userDetails = new User("username", "password", Collections.singletonList(adminAuthority));
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        responseDTO.setUserName("username");

        // Mock an expired token
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NzIwODM2NiwiZXhwIjoxNjk3MjE0MzY2fQ.CWwJF4aYxeFJNxLTLU6zoYdpUVrHv_HErZFYXiTC4QA";
        // Create a custom authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, expiredToken);
        // Create security context and set the Authentication
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // when
        when(service.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);

        // then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/users")
                        .header("Authorization", "Bearer " + expiredToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFilterForInvalidToken() throws Exception {
        UserDetails userDetails = new User("username", "password", Collections.emptyList());

        Mockito.when(jwtTokenProvider.validateToken(Mockito.anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/some-secure-endpoint")
                        .header("Authorization", "Bearer invalid-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFindAll() throws Exception {
        // given
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        List<UserDetailsResponseDTO> users = Collections.singletonList(responseDTO);

        // when
        when(service.findAll()).thenReturn(users);

        // then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findById() throws Exception {
        // given
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();

        // when
        when(service.findById(1)).thenReturn(responseDTO);

        // then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk());
    }


    @Test
    void update() {
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteCandidate() throws Exception {

        // when
        doNothing().when(service).delete(anyInt());

        // then
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void setRole() {
    }

    @Test
    void getService() {
    }
}