package com.example.Employee_learning_System.controller;

import com.example.Employee_learning_System.model.employee; // Assuming your model class is named Employee
import com.example.Employee_learning_System.repo.employeeRepo; // Assuming your repository interface is named EmployeeRepo
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin
public class employeecontroller {

    @Autowired
    private employeeRepo employeeRepository;

    // GET all employees
    @GetMapping
    public List<employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // GET employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<employee> getEmployeeById(@PathVariable Long id) {
        employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    // POST create a new employee
    @PostMapping("/addEmployee")
    public ResponseEntity<employee> createEmployee(@RequestBody employee emp) {
        try {
            String encodedPassword = Base64.getEncoder().encodeToString(emp.getPassword().getBytes());
            emp.setPassword(encodedPassword);
            employee savedEmployee = employeeRepository.save(emp);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // PUT update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<employee> updateEmployee(@PathVariable Long id, @RequestBody employee empDetails) {
        employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setFirstName(empDetails.getFirstName());
        employee.setLastName(empDetails.getLastName());
        employee.setEmail(empDetails.getEmail());
        employee.setDepartment(empDetails.getDepartment());
        employee.setPosition(empDetails.getPosition());
        employee.setHireDate(empDetails.getHireDate());
        employee.setBirthDate(empDetails.getBirthDate());
        employee.setAddress(empDetails.getAddress());
        employee.setPassword(empDetails.getPassword()); // Update password field

        employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
