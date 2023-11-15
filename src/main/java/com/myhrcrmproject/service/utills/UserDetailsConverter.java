package com.myhrcrmproject.service.utills;

import com.myhrcrmproject.domain.UserDetails;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import com.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsConverter {
    private final EmployeeConverter employeeConverter;
    private PasswordEncoder passwordEncoder;

    public UserDetailsConverter(EmployeeConverter employeeConverter, @Lazy PasswordEncoder passwordEncoder) {
        this.employeeConverter = employeeConverter;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDetailsResponseDTO toDTO(UserDetails entity) {
        return UserDetailsResponseDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .roleName(entity.getRole().getName())
                .employee(employeeConverter.toShortDTO(entity.getEmployee()))
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

    public UserDetails fromDTO(UserDetails entity, UserDetailsRequestDTO request) {
        Optional.ofNullable(request.getUserName()).ifPresent(entity::setUserName);
        Optional.ofNullable(request.getPassword()).ifPresent(
                password -> entity.setPassword(
                        passwordEncoder.encode(password)
                )
        );

        return entity;
    }

    public UserDetails newEntity() {
        return new UserDetails();
    }

    public UserDetailsShortResponseDTO toShortDTO(UserDetails entity) {
        return UserDetailsShortResponseDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .roleName(entity.getRole().getName())
                .build();
    }
}
