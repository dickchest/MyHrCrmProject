package com.myhrcrmproject.dto.addressDetailsDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for address details")
public class AddressDetailsDTO {

    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @Schema(description = "City", example = "Example City")
    private String city;

    @Schema(description = "State", example = "Example State")
    private String state;

    @Schema(description = "ZIP code", example = "12345")
    private String zip;

    @Schema(description = "Country", example = "Example Country")
    private String country;
}
