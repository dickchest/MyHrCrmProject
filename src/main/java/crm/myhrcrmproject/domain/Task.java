package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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
    private TaskStatus status = TaskStatus.OPENED; // default open

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_employee_id")
    private Employee employee;

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
