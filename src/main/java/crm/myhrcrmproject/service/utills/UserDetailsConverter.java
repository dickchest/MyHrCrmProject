package crm.myhrcrmproject.service.utills;

import crm.myhrcrmproject.domain.Role;
import crm.myhrcrmproject.domain.UserDetails;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsRequestDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsResponseDTO;
import crm.myhrcrmproject.dto.userDetailsDTO.UserDetailsShortResponseDTO;
import crm.myhrcrmproject.repository.RoleRepository;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsConverter {
    private final EmployeeConverter employeeConverter;
    private PasswordEncoder passwordEncoder;

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
//        Optional.ofNullable(request.getPassword()).ifPresent(entity::setPassword);
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
