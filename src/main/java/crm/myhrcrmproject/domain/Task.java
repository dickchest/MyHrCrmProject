package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Title must be not blank")
    @Size(min = 1, max = 255, message = "Title length must be between 1 and 255")
    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status; // default open

    @OneToOne
    @JoinColumn(name = "responsible_Employee_ID")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @OneToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdate;
}
