package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@DataJpaTest
//@ActiveProfiles("test")
class CandidatesRepositoryTest {
    @Autowired
    private CandidatesRepository candidatesRepository;

    @Test
    public void testRetrieveCandidateById() {
        Candidates candidate = new Candidates();
        candidate.setId(1);
        candidate.setFirstName("John");
        candidate.setLastName("Doe");
        candidate.setDateOfBirth(LocalDate.of(1990, 1, 1));

        // Save the candidate
        candidatesRepository.save(candidate);

        // Find the candidate
        Candidates foundCandidate = candidatesRepository.findById(1).orElse(null);
        assertThat(foundCandidate).isNotNull();
    }

}