package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.enums.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidate, Integer> {
    List<Candidate> findByCandidateStatus(CandidateStatus status);
    Optional<Candidate> findByEmail(String email);
}
