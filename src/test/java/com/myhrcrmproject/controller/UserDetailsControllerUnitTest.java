package com.myhrcrmproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import com.myhrcrmproject.service.auth.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserDetailsController.class)
@AutoConfigureMockMvc
class UserDetailsControllerUnitTest {

    @Value("/api/users")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl service;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testFindAll() throws Exception {
        // given
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        responseDTO.setId(1);
        responseDTO.setUserName("admin");
        List<UserDetailsResponseDTO> users = Collections.singletonList(responseDTO);

        // when
        when(service.findAll()).thenReturn(users);

        // then
        mockMvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("admin"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void findById() throws Exception {
        // given
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        responseDTO.setUserName("admin");

        // when
        when(service.findById(1)).thenReturn(responseDTO);

        // then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("admin"));
    }


    @Test
    @WithMockUser(roles="ADMIN")
    void testUpdate() throws Exception {
        // given
        UserDetailsRequestDTO requestDTO = new UserDetailsRequestDTO();
        requestDTO.setUserName("updatedUser");

        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO();
        responseDTO.setId(1);
        responseDTO.setUserName("admin");

        // Mock your service method
        when(service.update(1, requestDTO)).thenReturn(responseDTO);

        // then
        mockMvc.perform(put("/api/users/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("userName").value("admin"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void testDeleteUser() throws Exception {

        doNothing().when(service).delete(anyInt());

        mockMvc.perform(delete("/api/users/{id}", "2").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void setRole() throws Exception {
        mockMvc.perform(put("/api/users/setRole?id=2&role=manager")
                        .with(csrf()))
                .andExpect(status().isAccepted());
    }
}