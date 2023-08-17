package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import crm.myhrcrmproject.domain.enums.VacancyStatus;

import java.time.LocalDate;
@Entity
@Data
public class Vacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vacancyId;

    @NotBlank(message = "Job Title must be not blank")
    private String jobTitle;
    private String description;
    private Double salary;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VacancyStatus vacancyStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_Employee_ID")
    private Employees employees;
}
