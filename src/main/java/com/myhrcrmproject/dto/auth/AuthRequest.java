package com.myhrcrmproject.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(description = "User credentials")
public class AuthRequest {
    @Schema(description = "User name", example = "user")
    private String userName;
    @Schema(description = "User password", example = "TestPassword")
    private String password;

}
