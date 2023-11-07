package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Candidate;

import com.myhrcrmproject.domain.enums.CandidateStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CandidateRepositoryTest {
    static Candidate candidate;
    Candidate savedCandidate;

    @Autowired
    private CandidateRepository candidatesRepository;

    @BeforeAll
    public static void setup(){
        candidate = Candidate.builder()
                .id(1)
                .firstName("Oleg")
                .lastName("Kozlov")
                .status(CandidateStatus.ACTIVE)
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        savedCandidate = candidatesRepository.save(candidate);
    }
    @Test
    public void createCTest() {

        // Assert
        assertThat(savedCandidate).isNotNull();
        assertThat(savedCandidate.getId()).isGreaterThan(0);
        Assertions.assertEquals("Oleg", savedCandidate.getFirstName());
        Assertions.assertEquals("Kozlov", savedCandidate.getLastName());
    }



    @Test
    public void findByIdTest() {

        Integer id = savedCandidate.getId();

        // Act
        Candidate receivedCandidate = candidatesRepository.findById(id).get();

        // Assert
        assertThat(receivedCandidate).isNotNull();
        assertThat(receivedCandidate.getId()).isEqualTo(id);
    }

    @Test
    public void findAllTest() {

        List<Candidate> candidates = candidatesRepository.findAll();
        assertThat(candidates.size()).isGreaterThan(1);
    }

    @Test
    public void updateTest() {
        savedCandidate.setLastName("Petrov");
        Candidate updatedCandidate = candidatesRepository.save(savedCandidate);
        assertThat(updatedCandidate.getLastName()).isEqualTo("Petrov");
    }

    @Test
    public void deleteTest() {
        candidatesRepository.delete(savedCandidate);
        Optional<Candidate> optionalCandidate = candidatesRepository.findById(1);
        assertThat(optionalCandidate).isEmpty();
    }

}