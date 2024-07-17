package com.example.Employee_learning_System.repo;

import com.example.Employee_learning_System.model.enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface enrollmentRepo extends JpaRepository<enrollment, Long> {

}
