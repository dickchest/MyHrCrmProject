package com.myhrcrmproject.dto.userDetailsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsShortResponseDTO {
    private Integer id;
    private String userName;
    private String roleName;
}
