package crm.myhrcrmproject.domain;

import crm.myhrcrmproject.domain.enums.ContractType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Start date must be not blank")
    private LocalDate startDate;

    @NotNull(message = "End date must be not blank")
    private LocalDate endDate;

    @NotNull(message = "Salary must be not blank")
    private Double salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractType contractType = ContractType.FULL_TIME;

    @NotNull(message = "Candidate should not be blank")
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @NotNull(message = "Employee should not be blank")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_employee_id")
    private Employee employee;

    @NotNull(message = "Client should not be blank")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
