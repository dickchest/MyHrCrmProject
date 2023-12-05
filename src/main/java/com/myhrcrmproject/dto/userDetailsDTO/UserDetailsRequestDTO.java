package com.myhrcrmproject.dto.userDetailsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "User details for registering new user")
public class UserDetailsRequestDTO {

    @Schema(description = "User name", example = "newUser")
    private String userName;

    @Schema(description = "User password", example = "newUserPassword")
    private String password;

    @Schema(description = "User email", example = "newUser@email.com")
    private String email;
}
