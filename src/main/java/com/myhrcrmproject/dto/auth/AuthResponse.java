package com.myhrcrmproject.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @Schema(description = "User token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJUZXN0VXNlcjMiLCJpYXQiOjE3MDE3MjAxNDksImV4cCI6MTcwMTcyNjE0OX0.JeFSCnNK5Wmxpt3CbwwVBw6sa_ga0zKG4EZ3qIXnzx")
    private String token;
}
