// repository/GradeRepository.java
package com.student_grade_management.repository;

import com.student_grade_management.model.Grade;
import com.student_grade_management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudent(Student student);

    @Query("SELECT AVG(g.score) FROM Grade g WHERE g.student.studentId = :studentId")
    Double calculateAverageScore(@Param("studentId") Long studentId);

    @Query("SELECT COUNT(g) FROM Grade g WHERE g.student.studentId = :studentId")
    Long countGradesByStudentId(@Param("studentId") Long studentId);
}