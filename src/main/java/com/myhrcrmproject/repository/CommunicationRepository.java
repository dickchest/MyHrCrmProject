package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.*;
import com.myhrcrmproject.domain.enums.CommunicationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
    List<Communication> findByEmployee(Employee employee);

    List<Communication> findByClient(Client client);

    List<Communication> findByCandidate(Candidate candidate);

    List<Communication> findByVacancy(Vacancy candidate);

    List<Communication> findAllByCommunicationType(CommunicationType type);
}
