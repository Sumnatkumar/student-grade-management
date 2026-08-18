// model/Grade.java
package com.student_grade_management.model;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false, length = 50)
    private String subject;

    @Column(nullable = false)
    private Double score;

    @Column(name = "grade_letter", nullable = false, length = 2)
    private String gradeLetter;

    @Column(nullable = false, length = 20)
    private String semester;

    public Grade() {}

    public Grade(Student student, String subject, Double score, String semester) {
        this.student = student;
        this.subject = subject;
        this.score = score;
        this.semester = semester;
        this.gradeLetter = calculateGradeLetter(score);
    }

    // Getters and Setters
    public Long getGradeId() { return gradeId; }
    public void setGradeId(Long gradeId) { this.gradeId = gradeId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Double getScore() { return score; }
    public void setScore(Double score) {
        this.score = score;
        this.gradeLetter = calculateGradeLetter(score);
    }

    public String getGradeLetter() { return gradeLetter; }
    public void setGradeLetter(String gradeLetter) { this.gradeLetter = gradeLetter; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    private String calculateGradeLetter(Double score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return String.format("Subject: %s | Score: %.2f | Grade: %s | Semester: %s",
                subject, score, gradeLetter, semester);
    }
}