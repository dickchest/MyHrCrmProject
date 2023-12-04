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
@Schema(description = "Short response DTO for client details")
public class ClientShortResponseDTO {

    @Schema(description = "Client ID", example = "1")
    private Integer id;

    @Schema(description = "Company name", example = "Example Company")
    private String companyName;
}
