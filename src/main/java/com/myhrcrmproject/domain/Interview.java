package com.myhrcrmproject.domain;

import com.myhrcrmproject.domain.enums.InterviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Date must be not blank")
    private LocalDate date;

    @NotNull(message = "Time must be not blank")
    private LocalTime time;

    @NotBlank(message = "Location must be not blank")
    @Size(min = 1, max = 255, message = "Location's length must be between 1 and 255")
    private String location;

    private String comments;

    @NotNull(message = "Candidate should not be blank")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_ID")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewStatus status = InterviewStatus.SCHEDULED;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
