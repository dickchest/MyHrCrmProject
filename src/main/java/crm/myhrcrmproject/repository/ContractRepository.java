package crm.myhrcrmproject.repository;

import crm.myhrcrmproject.domain.Candidate;
import crm.myhrcrmproject.domain.Client;
import crm.myhrcrmproject.domain.Contract;
import crm.myhrcrmproject.domain.Employee;
import crm.myhrcrmproject.domain.enums.ContractType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findAllByContractType(ContractType type);

    List<Contract> findByEmployee(Employee employee);

    List<Contract> findByClient(Client client);

    List<Contract> findByCandidate(Candidate candidate);
    List<Contract> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
}
