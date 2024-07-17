package com.example.Employee_learning_System.controller;

import com.example.Employee_learning_System.model.course;
import com.example.Employee_learning_System.repo.courseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class coursecontroller {

    private final courseRepo courseRepository;

    @Autowired
    public coursecontroller(courseRepo courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<List<course>> getAllCourses() {
        List<course> courses = courseRepository.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<course> getCourseById(@PathVariable("id") Long id) {
        Optional<course> courseOptional = courseRepository.findById(id);
        return courseOptional.map(course -> new ResponseEntity<>(course, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<course> createCourse(@RequestBody course course) {
        course savedCourse = courseRepository.save(course);
        return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<course> updateCourse(@PathVariable("id") Long id, @RequestBody course course) {
        Optional<course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        course updatedCourse = courseRepository.save(course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        Optional<course> courseOptional = courseRepository.findById(id);
        if (!courseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
