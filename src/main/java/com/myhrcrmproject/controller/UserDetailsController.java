package com.myhrcrmproject.controller;

import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import com.myhrcrmproject.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Getter
@Slf4j
@Tag(
        name = "User Details Controller",
        description = "APIs for managing user details. Requires admin role."
)
public class UserDetailsController {
    private final UserDetailsServiceImpl service;

    @GetMapping
    @Operation(
            summary = "Get all user details",
            description = "Retrieve a list of all user details. Requires admin role."
    )
    public ResponseEntity<List<UserDetailsResponseDTO>> findAll() {
        log.info("ENTER FIND ALL CONTROLLER");
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user detail by ID",
            description = "Retrieve an user detail by their ID. Requires admin role."
    )
    public ResponseEntity<UserDetailsResponseDTO> findById(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail", example = "1")
            Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an user detail",
            description = "Update an existing user detail with the provided details. Requires admin role."
    )
    public ResponseEntity<UserDetailsResponseDTO> update(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail", example = "1")
            Integer id,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing updated user detail details")
            UserDetailsRequestDTO requestDTO) {
        UserDetailsResponseDTO responseDTO = service.update(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete an user detail",
            description = "Delete an user detail by their ID. Requires admin role."
    )
    public void delete(
            @PathVariable("id")
            @Parameter(description = "ID of the user detail to delete", example = "1")
            Integer id) {
        service.delete(id);
    }

    @PutMapping("/setRole") // запрос: api/users/setRole?id=2&role=manager
    @Operation(
            summary = "Update an user details role",
            description = "Update an existing user details with the provided role. Requires admin role."
    )
    public ResponseEntity<UserDetailsShortResponseDTO> setRole(
            @RequestParam("id")
            @Parameter(description = "ID of the user detail", example = "2")
            Integer id,
            @RequestParam("role")
            @Parameter(description = "role", example = "admin")
            String request) {
        log.info("Set Role controller has been called");
        return new ResponseEntity<>(service.setRole(id, request), HttpStatus.ACCEPTED);
    }

}
