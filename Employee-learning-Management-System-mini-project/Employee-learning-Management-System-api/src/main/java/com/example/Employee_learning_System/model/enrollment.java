package com.example.Employee_learning_System.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "enrollments")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private employee employee;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private course course;

    @Column(name = "enrollment_date")
    private Date enrollmentDate;

    @Column(name = "is_completed")
    private boolean isCompleted = false; // Default value is false

}
