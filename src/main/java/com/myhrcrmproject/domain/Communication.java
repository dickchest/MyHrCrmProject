package com.myhrcrmproject.domain;

import com.myhrcrmproject.domain.enums.CommunicationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Communication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Communication Date & Time should be not blank")
    private LocalDateTime communicationDateTime = LocalDateTime.now();

    @NotNull(message = "Communication type should be not blank")
    @Enumerated(EnumType.STRING)
    private CommunicationType communicationType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsible_employee_id")
    private Employee employee;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
