package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.Client;
import com.myhrcrmproject.domain.Contract;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.enums.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findAllByContractType(ContractType type);

    List<Contract> findByEmployee(Employee employee);

    List<Contract> findByClient(Client client);

    List<Contract> findByCandidate(Candidate candidate);
    List<Contract> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
}
