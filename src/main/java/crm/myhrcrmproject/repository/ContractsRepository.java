package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidates;
import crm.myhrcrmproject.domain.Contracts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contracts, Integer> {
}
