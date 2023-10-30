package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.ContactDetails;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.CandidateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    List<Candidate> findByStatus(CandidateStatus status);
    List<Candidate> findByVacancy(Vacancy vacancy);
//    Optional<Candidate> findByEmail(String email);
    Optional<Candidate> findByContactDetails(ContactDetails contactDetails);
}
