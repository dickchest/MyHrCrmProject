package com.myhrcrmproject.dto.clientDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for client request")
public class ClientRequestDTO {

    @Schema(description = "Company name", example = "Example Company")
    private String companyName;

    @Schema(description = "Description", example = "Example description about the client")
    private String description;
}