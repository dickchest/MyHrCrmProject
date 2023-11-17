package com.myhrcrmproject.repository;

import com.myhrcrmproject.domain.Candidate;
import com.myhrcrmproject.domain.Employee;
import com.myhrcrmproject.domain.Task;
import com.myhrcrmproject.domain.Vacancy;
import com.myhrcrmproject.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findAllByEmployee(Employee employee);

    List<Task> findAllByCandidate(Candidate candidate);

    List<Task> findAllByVacancy(Vacancy candidate);

    List<Task> findAllByStartDate(LocalDate date);

    List<Task> findAllByStartDateAndEmployee(LocalDate date, Employee employee);

    List<Task> findAllByCandidateAndEmployee(Candidate candidate, Employee employee);

    List<Task> findAllByStatusAndEmployee(TaskStatus status, Employee employee);

    List<Task> findAllByVacancyAndEmployee(Vacancy vacancy, Employee employee);
}
