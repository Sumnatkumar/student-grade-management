// service/GradeService.java
package com.student_grade_management.service;

import com.student_grade_management.model.Grade;
import com.student_grade_management.model.Student;
import com.student_grade_management.repository.GradeRepository;
import com.student_grade_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Grade addGrade(Long studentId, Grade grade) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        grade.setStudent(student);
        return gradeRepository.save(grade);
    }

    public List<Grade> getGradesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        return gradeRepository.findByStudent(student);
    }

    public Grade updateGrade(Grade grade) {
        if (!gradeRepository.existsById(grade.getGradeId())) {
            throw new RuntimeException("Grade not found with ID: " + grade.getGradeId());
        }
        return gradeRepository.save(grade);
    }

    public void deleteGrade(Long gradeId) {
        if (!gradeRepository.existsById(gradeId)) {
            throw new RuntimeException("Grade not found with ID: " + gradeId);
        }
        gradeRepository.deleteById(gradeId);
    }

    public Double calculateAverageScore(Long studentId) {
        return gradeRepository.calculateAverageScore(studentId);
    }

    public Long getGradeCount(Long studentId) {
        return gradeRepository.countGradesByStudentId(studentId);
    }
}