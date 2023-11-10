package com.myhrcrmproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotBlank(message = "Name must be not blank")
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 15")
    private String firstName;

//    @NotBlank(message = "Lastname must be not blank")
    @Size(min = 1, max = 50, message = "Lastname length must be between 1 and 50")
    private String lastName;

    private String position;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Vacancy> vacancyList;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private UserDetails userDetails;
}
