package com.example.Employee_learning_System.controller;

import com.example.Employee_learning_System.model.enrollment;
import com.example.Employee_learning_System.repo.enrollmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@CrossOrigin
public class enrollmentcontroller {

    private final enrollmentRepo enrollmentRepository;

    @Autowired
    public enrollmentcontroller(enrollmentRepo enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // Get all enrollments
    @GetMapping
    public List<enrollment> getAllEnrollments() {
        System.out.println("here");
        return enrollmentRepository.findAll();
    }

    // Get enrollment by ID
    @GetMapping("/{id}")
    public enrollment getEnrollmentById(@PathVariable("id") Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id " + id));
    }

    // Create a new enrollment
    @PostMapping("/addenrollment")
    public enrollment createEnrollment(@RequestBody enrollment enrollment) {
        enrollment.setCompleted(false);
        return enrollmentRepository.save(enrollment);
    }

    // Update enrollment
    @PutMapping("/{id}")
    public enrollment updateEnrollment(@PathVariable("id") Long id, @RequestBody enrollment updatedEnrollment) {
        return enrollmentRepository.findById(id)
                .map(enrollment -> {
                    enrollment.setEmployee(updatedEnrollment.getEmployee());
                    enrollment.setCourse(updatedEnrollment.getCourse());
                    enrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
                    enrollment.setCompleted(updatedEnrollment.isCompleted());
                    return enrollmentRepository.save(enrollment);
                })
                .orElseThrow(() -> new RuntimeException("Enrollment not found with id " + id));
    }

    // Delete enrollment
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable("id") Long id) {
        enrollmentRepository.deleteById(id);
    }
}
