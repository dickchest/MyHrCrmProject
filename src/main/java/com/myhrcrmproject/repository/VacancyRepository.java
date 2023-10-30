package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findByStatus(VacancyStatus status);
    List<Vacancy> findByEmployee(Employee employee);
}
