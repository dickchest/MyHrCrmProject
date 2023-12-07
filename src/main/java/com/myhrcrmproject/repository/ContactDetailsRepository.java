package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Integer> {
    Optional<ContactDetails> findByEmail(String email);
}
