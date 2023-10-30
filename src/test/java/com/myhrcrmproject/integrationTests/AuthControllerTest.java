package com.myhrcrmproject.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.dto.auth.AuthRequest;
import com.myhrcrmproject.dto.auth.AuthResponse;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.repository.UserDetailsRepository;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import com.myhrcrmproject.service.auth.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@Sql("/database/add_test_data.sql")
//@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureTestEntityManager
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDetailsRepository repository;

    @Autowired
    private UserDetailsServiceImpl service;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        // записываем в бд тестовую запись
        final UserDetailsRequestDTO createRequest = new UserDetailsRequestDTO()
                .setUserName("TestName")
                .setPassword("TestPassword")
                .setEmail("Test@email.com");
        service.create(createRequest);
    }

    @Test
    void whenLogin_withoutAccount_thenReturnStatus404() throws Exception {
        final AuthRequest incorrectRequest = new AuthRequest()
                .setUserName("TestName1")
                .setPassword("TestPassword");

        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(incorrectRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User with name: TestName1 not found!")));
    }

    @Test
    void whenLogin_withExistingAccount_thenReturnStatus200() throws Exception {
        final AuthRequest correctRequest = new AuthRequest()
                .setUserName("TestName")
                .setPassword("TestPassword");

        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(correctRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
    }

    @Test
    void registerNewUser() throws Exception {
        final UserDetailsRequestDTO createRequest = new UserDetailsRequestDTO()
                .setUserName("TestName1")
                .setPassword("TestPassword1")
                .setEmail("Test1@email.com");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        UserDetails user = repository.findByUserName("TestName1").get();
        assertThat(user.getUserName()).isEqualTo("TestName1");
    }

    @Test
    void whenRegisterNewUser_WithExistedName_ReturnStatus400() throws Exception {
        final UserDetailsRequestDTO createRequest = new UserDetailsRequestDTO()
                .setUserName("TestName")
                .setPassword("TestPassword")
                .setEmail("Test@email.com");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("User with name TestName already exists")));
    }

    @Test
    void whenRegisterNewUser_WithIncorrectEmail_ReturnStatus400() throws Exception {
        final UserDetailsRequestDTO incorrectCreateRequest = new UserDetailsRequestDTO()
                .setUserName("TestName2")
                .setPassword("TestPassword2");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(incorrectCreateRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email must be not blank")));
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        final AuthRequest correctRequest = new AuthRequest()
                .setUserName("TestName")
                .setPassword("TestPassword");

        MvcResult postAuthTokenResult = mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(correctRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String resultAsTokenString = postAuthTokenResult.getResponse().getContentAsString();
        AuthResponse securityToken = mapper.readValue(resultAsTokenString, AuthResponse.class);
        String actualName = tokenProvider.getUserNameFromJwt(securityToken.getToken());

        Assertions.assertEquals(correctRequest.getUserName(), actualName);
    }
}