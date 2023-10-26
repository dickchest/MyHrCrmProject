package crm.myhrcrmproject.controller;

import crm.myhrcrmproject.dto.auth.AuthRequest;
import crm.myhrcrmproject.dto.auth.AuthResponse;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.service.UserDetailsServiceImpl;
import crm.myhrcrmproject.service.auth.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsServiceImpl service;

    @PostMapping
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
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
    public ResponseEntity<UserDetailsResponseDTO> registerNewUser(@Valid @RequestBody UserDetailsRequestDTO request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }
}
