package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
//    List<UserDetails> findAllByEmployee(Employee employee);

    Optional<UserDetails> findByUserName(String userName);

}
