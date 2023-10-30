package com.myhrcrmproject.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class UserDetailsControllerTest {

    @Value("/api/users")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAll_shouldReturnOkStatus() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].userName").value("admin"))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    @WithMockUser
    void simpleUser_findAll_shouldReturnStatus403() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    void notLoggedIn_shouldNotSeeSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindById_shouldReturnStatus200() throws Exception {
        mockMvc.perform(get(basePath+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userName").value("admin"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testFindById_notExistingUser_shouldReturnStatus404() throws Exception {
        mockMvc.perform(get(basePath+"/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User with id 0 not found")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        UserDetailsRequestDTO userDetailsRequestDTO = new UserDetailsRequestDTO();
        userDetailsRequestDTO.setUserName("TestAdmin");

        mockMvc.perform(put(basePath+"/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetailsRequestDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.userName").value("TestAdmin"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCandidate() throws Exception {
        mockMvc.perform(delete(basePath+"/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void testSetRole_shouldReturnAccepted() throws Exception {
        mockMvc.perform(put("/api/users/setRole?id=2&role=manager")
                        .with(csrf()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.roleName").value("manager"));
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void testSetRole_notExistingRole_shouldReturn404() throws Exception {
        mockMvc.perform(put("/api/users/setRole?id=2&role=errorrole")
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Role errorrole not found!")));
    }


}