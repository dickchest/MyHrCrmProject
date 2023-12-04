package com.myhrcrmproject.dto.clientDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for client response")
public class ClientResponseDTO {

    @Schema(description = "Client ID", example = "1")
    private Integer id;

    @Schema(description = "Company name", example = "Example Company")
    private String companyName;

    @Schema(description = "Description", example = "Example description about the client")
    private String description;
}
