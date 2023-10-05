package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Compensation;
import crm.myhrcrmproject.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    List<Compensation> findByCandidate(Candidate candidate);

    List<Compensation> findByContract(Contract contract);
}
