package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.Compensation;
import com.myhrcrmproject.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompensationRepository extends JpaRepository<Compensation, Integer> {
    List<Compensation> findByCandidate(Candidate candidate);

    List<Compensation> findByContract(Contract contract);
}
