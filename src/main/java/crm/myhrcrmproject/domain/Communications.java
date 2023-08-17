package crm.myhrcrmproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Communications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Communication Date & Time should be not blank")
    private LocalDateTime communicationDateTime;

    @NotBlank(message = "Communication type should be not blank")
    private String communicationType;   // todo сделать енум или таблицу

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private ClientContacts contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidates candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacansy_id")
    private Vacancies vacancy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_employee_id")
    private Employees employees;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdate;
}
