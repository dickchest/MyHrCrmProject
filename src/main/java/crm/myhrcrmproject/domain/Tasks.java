package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.TasksStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Tasks {
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
    private TasksStatus status; // default open

    @OneToOne
    @JoinColumn(name = "responsible_Employee_ID")
    private Employees employees;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;

    @OneToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancies vacancy;

}
