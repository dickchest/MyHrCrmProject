package com.myhrcrmproject.dto.userDetailsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User details DTO for short response")
public class UserDetailsShortResponseDTO {

    @Schema(description = "User details ID", example = "1")
    private Integer id;

    @Schema(description = "User name", example = "TestUser1")
    private String userName;

    @Schema(description = "User role name", example = "manager")
    private String roleName;
}
