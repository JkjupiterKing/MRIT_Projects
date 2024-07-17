package com.example.Employee_learning_System.repo;

import com.example.Employee_learning_System.model.course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseRepo extends JpaRepository<course, Long> {

}
