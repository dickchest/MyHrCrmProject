package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import crm.myhrcrmproject.domain.enums.VacancyStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Job Title must be not blank")
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VacancyStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_Employee_ID")
    private Employee employee;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL)
    private List<Candidate> candidate;
}
