package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidates, Integer> {
}
