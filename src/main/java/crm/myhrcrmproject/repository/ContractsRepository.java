package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractsRepository extends JpaRepository<Contract, Integer> {
}
