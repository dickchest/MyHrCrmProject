package com.myhrcrmproject.dto.taskDTO;

import com.myhrcrmproject.domain.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskShortResponseDTO {

    @Schema(description = "Task ID", example = "1")
    private Integer id;

    @Schema(description = "Title of the task", example = "Task 1")
    private String title;

    @Schema(description = "Description of the task", example = "Complete project report")
    private String description;

    @Schema(description = "Start date of the task", example = "2023-10-28")
    private LocalDate startDate;

    @Schema(description = "End date of the task", example = "2023-11-10")
    private LocalDate endDate;

    @Schema(description = "Status of the task", example = "IN_PROCESS")
    private TaskStatus status;
}