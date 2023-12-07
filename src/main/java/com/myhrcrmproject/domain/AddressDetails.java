package com.myhrcrmproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Entity class representing address details.
 *
 * <p>This class is annotated with {@code @Entity} to indicate that it is a JPA entity. It represents
 * the address details of a candidate, employee, or any relevant entity in the HR CRM system.
 *
 * <p>The class includes fields such as street, city, state, zip code, and country. It also contains
 * standard JPA annotations like {@code @Id} and {@code @GeneratedValue} for database mapping.
 *
 * <p>The use of {@code @Getter}, {@code @Setter}, {@code @NoArgsConstructor}, {@code @AllArgsConstructor},
 * {@code @EqualsAndHashCode}, and {@code @ToString} annotations is to automatically generate standard
 * boilerplate code, improving code readability and maintainability.
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 * @since 2023-12-01
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddressDetails {
    /**
     * The unique identifier for an address details record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The street name of the address.
     */
    private String street;

    /**
     * The city of the address.
     */
    private String city;

    /**
     * The state or region of the address.
     */
    private String state;

    /**
     * The postal code or zip code of the address.
     */
    private String zip;

    /**
     * The country of the address.
     */
    private String country;
}
