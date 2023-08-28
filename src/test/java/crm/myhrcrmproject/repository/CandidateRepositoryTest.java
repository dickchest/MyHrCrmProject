package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidate;

import crm.myhrcrmproject.domain.enums.CandidateStatus;
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
    private CandidatesRepository candidatesRepository;

    @BeforeAll
    public static void setup(){
        candidate = Candidate.builder()
                .firstName("Oleg")
                .lastName("Kozlov")
                .email("test@example.com")
                .candidateStatus(CandidateStatus.ACTIVE)
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
        Assertions.assertEquals("test@example.com", savedCandidate.getEmail());
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
        assertThat(candidates.size()).isEqualTo(1);
    }

    @Test
    public void updateTest() {
        savedCandidate.setEmail("1234@test.com");
        Candidate updatedCandidate = candidatesRepository.save(savedCandidate);
        assertThat(updatedCandidate.getEmail()).isEqualTo("1234@test.com");
    }

    @Test
    public void deleteTest() {
        candidatesRepository.delete(savedCandidate);
        Optional<Candidate> optionalCandidate = candidatesRepository.findByEmail("1234@test.com");

        assertThat(optionalCandidate).isEmpty();
    }

}