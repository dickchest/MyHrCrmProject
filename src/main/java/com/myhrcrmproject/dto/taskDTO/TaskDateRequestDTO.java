package com.myhrcrmproject.dto.taskDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for task date request")
public class TaskDateRequestDTO {

    @Schema(description = "Date of the task", example = "2023-10-30")
    private LocalDate date;
}
