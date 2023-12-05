package com.myhrcrmproject.dto.taskDTO;

import com.myhrcrmproject.domain.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for task request")
public class TaskRequestDTO {

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

    @Schema(description = "Associated employee ID", example = "1")
    private Integer employeeId;

    @Schema(description = "Associated candidate ID", example = "1")
    private Integer candidateId;

    @Schema(description = "Associated vacancy ID", example = "1")
    private Integer vacancyId;
}