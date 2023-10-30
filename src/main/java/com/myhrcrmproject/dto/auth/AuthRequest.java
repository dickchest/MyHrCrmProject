package com.myhrcrmproject.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequest {
    private String userName;
    private String password;

}
