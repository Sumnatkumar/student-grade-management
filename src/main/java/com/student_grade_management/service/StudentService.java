// service/StudentService.java
package com.student_grade_management.service;

import com.student_grade_management.model.Student;
import com.student_grade_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeService gradeService;

    public Student addStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getStudentId())) {
            throw new RuntimeException("Student not found with ID: " + student.getStudentId());
        }
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    public double calculateGPA(Long studentId) {
        Double average = gradeService.calculateAverageScore(studentId);
        return average != null ? average : 0.0;
    }

    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmail(email);
    }
}