package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.auth.AuthRequest;
import com.myhrcrmproject.dto.auth.AuthResponse;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import com.myhrcrmproject.service.auth.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

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
