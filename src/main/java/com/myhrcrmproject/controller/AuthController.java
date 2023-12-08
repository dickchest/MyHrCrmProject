/**
 * This package contains controllers responsible for handling authentication,
 * user registration, and related operations.
 */
package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.auth.AuthRequest;
import com.myhrcrmproject.dto.auth.AuthResponse;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import com.myhrcrmproject.service.auth.JwtTokenProvider;
import com.myhrcrmproject.service.auth.SecurityHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication and user registration endpoints.
 *
 * <p>This class provides RESTful endpoints for authenticating users and
 * registering new users. It interacts with the {@code AuthenticationManager}
 * for user authentication and the {@code UserDetailsServiceImpl} for user registration.
 *
 * <p>Additionally, it uses the {@code JwtTokenProvider} for generating JWT tokens
 * upon successful user authentication.
 *
 * <p>The class is annotated with {@code @RestController} to indicate its role as
 * a Spring REST controller, and it is mapped to the "/api/auth" endpoint.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication and User Registration",
        description = "Endpoints for user authentication and registration"
)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl service;
    private final SecurityHelper securityHelper;


    /**
     * Endpoint for authenticating a user and generating a JWT token.
     *
     * @param authRequest The request body containing user credentials (username and password).
     * @return A {@code ResponseEntity} with the JWT token upon successful authentication.
     */
    @PostMapping
    @Operation(
            summary = "Authenticate User",
            description = "Endpoint to authenticate a user and generate a JWT token."
    )
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing user credentials (username and password)."
            ) AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserName(),
                        authRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authRequest.getUserName());
        Integer employeeId = securityHelper.getCurrentAuthEmployee().get().getId();
        return ResponseEntity.ok(new AuthResponse(jwt, employeeId));
    }

    /**
     * Endpoint for registering a new user.
     *
     * @param request The request body containing user credentials and email.
     * @return A {@code ResponseEntity} with the details of the newly registered user.
     */
    @PostMapping("/register")
    @Operation(
            summary = "Register New User",
            description = "Endpoint to register a new user."
    )
    public ResponseEntity<UserDetailsResponseDTO> registerNewUser(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing user credentials and email."
            ) UserDetailsRequestDTO request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }
}
