package com.example.Employee_learning_System.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Employee_learning_System.model.employee;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface employeeRepo extends JpaRepository<employee, Long> {
    Optional<employee> findByEmail(String email);
}

