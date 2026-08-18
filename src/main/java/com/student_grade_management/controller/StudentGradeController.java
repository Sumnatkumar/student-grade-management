// controller/StudentGradeController.java
package com.student_grade_management.controller;

import com.student_grade_management.model.Grade;
import com.student_grade_management.model.Student;
import com.student_grade_management.service.GradeService;
import com.student_grade_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Controller
public class StudentGradeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    private Scanner scanner;

    public void startConsoleApp() {
        scanner = new Scanner(System.in);
        System.out.println("=====================================");
        System.out.println("  STUDENT GRADE MANAGEMENT SYSTEM   ");
        System.out.println("=====================================");

        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageGrades();
                    break;
                case 3:
                    viewStudentReport();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Grades");
        System.out.println("3. View Student Report");
        System.out.println("4. View All Students");
        System.out.println("5. Exit");
    }

    private void manageStudents() {
        System.out.println("\n--- STUDENT MANAGEMENT ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student Details");
        System.out.println("5. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                updateStudent();
                break;
            case 3:
                deleteStudent();
                break;
            case 4:
                viewStudentDetails();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addStudent() {
        try {
            System.out.println("\n--- ADD STUDENT ---");
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            Student student = new Student(firstName, lastName, email);
            Student savedStudent = studentService.addStudent(student);
            System.out.println("Student added successfully! Student ID: " + savedStudent.getStudentId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.println("\n--- UPDATE STUDENT ---");
            Long id = getLongInput("Enter Student ID to update: ");
            Optional<Student> studentOpt = studentService.getStudentById(id);

            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            Student student = studentOpt.get();
            System.out.println("Current Details: " + student);

            System.out.print("Enter new First Name (press Enter to keep current): ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) student.setFirstName(firstName);

            System.out.print("Enter new Last Name (press Enter to keep current): ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) student.setLastName(lastName);

            System.out.print("Enter new Email (press Enter to keep current): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (studentService.existsByEmail(email) && !student.getEmail().equals(email)) {
                    System.out.println("Email already exists for another student!");
                    return;
                }
                student.setEmail(email);
            }

            studentService.updateStudent(student);
            System.out.println("Student updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.println("\n--- DELETE STUDENT ---");
            Long id = getLongInput("Enter Student ID to delete: ");
            Optional<Student> studentOpt = studentService.getStudentById(id);

            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            System.out.println("Student to delete: " + studentOpt.get());
            System.out.print("Are you sure? (y/n): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                studentService.deleteStudent(id);
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudentDetails() {
        try {
            System.out.println("\n--- STUDENT DETAILS ---");
            Long id = getLongInput("Enter Student ID: ");
            Optional<Student> studentOpt = studentService.getStudentById(id);

            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            Student student = studentOpt.get();
            System.out.println("\nStudent Information:");
            System.out.println("ID: " + student.getStudentId());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("Enrollment Date: " + student.getEnrollmentDate());

            List<Grade> grades = gradeService.getGradesByStudentId(id);
            if (!grades.isEmpty()) {
                System.out.println("\nGrades:");
                for (Grade grade : grades) {
                    System.out.println("  " + grade);
                }
                double gpa = studentService.calculateGPA(id);
                System.out.printf("GPA: %.2f\n", gpa);
            } else {
                System.out.println("\nNo grades recorded.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void manageGrades() {
        System.out.println("\n--- GRADE MANAGEMENT ---");
        System.out.println("1. Add Grade");
        System.out.println("2. Update Grade");
        System.out.println("3. Delete Grade");
        System.out.println("4. View Student Grades");
        System.out.println("5. Back to Main Menu");

        int choice = getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                addGrade();
                break;
            case 2:
                updateGrade();
                break;
            case 3:
                deleteGrade();
                break;
            case 4:
                viewStudentGrades();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private void addGrade() {
        try {
            System.out.println("\n--- ADD GRADE ---");
            Long studentId = getLongInput("Enter Student ID: ");

            Optional<Student> studentOpt = studentService.getStudentById(studentId);
            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();
            Double score = getDoubleInput("Enter Score (0-100): ");
            System.out.print("Enter Semester (e.g., Fall 2023): ");
            String semester = scanner.nextLine();

            Grade grade = new Grade(studentOpt.get(), subject, score, semester);
            gradeService.addGrade(studentId, grade);
            System.out.println("Grade added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateGrade() {
        try {
            System.out.println("\n--- UPDATE GRADE ---");
            Long studentId = getLongInput("Enter Student ID: ");
            List<Grade> grades = gradeService.getGradesByStudentId(studentId);

            if (grades.isEmpty()) {
                System.out.println("No grades found for this student.");
                return;
            }

            System.out.println("\nCurrent Grades:");
            for (Grade g : grades) {
                System.out.println("  ID: " + g.getGradeId() + " | " + g);
            }

            Long gradeId = getLongInput("Enter Grade ID to update: ");
            Grade grade = grades.stream()
                    .filter(g -> g.getGradeId().equals(gradeId))
                    .findFirst()
                    .orElse(null);

            if (grade == null) {
                System.out.println("Grade not found!");
                return;
            }

            System.out.print("Enter new Subject (press Enter to keep current): ");
            String subject = scanner.nextLine();
            if (!subject.isEmpty()) grade.setSubject(subject);

            Double score = getDoubleInput("Enter new Score (0-100, -1 to keep current): ");
            if (score >= 0) grade.setScore(score);

            System.out.print("Enter new Semester (press Enter to keep current): ");
            String semester = scanner.nextLine();
            if (!semester.isEmpty()) grade.setSemester(semester);

            gradeService.updateGrade(grade);
            System.out.println("Grade updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteGrade() {
        try {
            System.out.println("\n--- DELETE GRADE ---");
            Long gradeId = getLongInput("Enter Grade ID to delete: ");

            System.out.print("Are you sure? (y/n): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                gradeService.deleteGrade(gradeId);
                System.out.println("Grade deleted successfully!");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudentGrades() {
        try {
            System.out.println("\n--- STUDENT GRADES ---");
            Long studentId = getLongInput("Enter Student ID: ");
            Optional<Student> studentOpt = studentService.getStudentById(studentId);

            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            System.out.println("\nStudent: " + studentOpt.get().getFullName());
            List<Grade> grades = gradeService.getGradesByStudentId(studentId);

            if (grades.isEmpty()) {
                System.out.println("No grades recorded.");
                return;
            }

            System.out.println("\nGrades:");
            for (Grade grade : grades) {
                System.out.println("  " + grade);
            }
            double gpa = studentService.calculateGPA(studentId);
            System.out.printf("GPA: %.2f\n", gpa);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudentReport() {
        try {
            System.out.println("\n--- STUDENT REPORT ---");
            Long studentId = getLongInput("Enter Student ID: ");
            Optional<Student> studentOpt = studentService.getStudentById(studentId);

            if (studentOpt.isEmpty()) {
                System.out.println("Student not found!");
                return;
            }

            Student student = studentOpt.get();
            List<Grade> grades = gradeService.getGradesByStudentId(studentId);

            System.out.println("\n========================================");
            System.out.println("           STUDENT REPORT              ");
            System.out.println("========================================");
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmail());
            System.out.println("----------------------------------------");

            if (grades.isEmpty()) {
                System.out.println("No grades recorded.");
            } else {
                System.out.println("Subjects and Grades:");
                System.out.println("----------------------------------------");
                for (Grade grade : grades) {
                    System.out.printf("%-15s | Score: %6.2f | Grade: %s | Semester: %s\n",
                            grade.getSubject(), grade.getScore(),
                            grade.getGradeLetter(), grade.getSemester());
                }
                System.out.println("----------------------------------------");
                double gpa = studentService.calculateGPA(studentId);
                System.out.printf("Overall GPA: %.2f\n", gpa);

                double totalScore = grades.stream().mapToDouble(Grade::getScore).sum();
                double average = totalScore / grades.size();
                System.out.printf("Average Score: %.2f\n", average);
                System.out.println("Total Subjects: " + grades.size());
            }
            System.out.println("========================================");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllStudents() {
        try {
            System.out.println("\n--- ALL STUDENTS ---");
            List<Student> students = studentService.getAllStudents();

            if (students.isEmpty()) {
                System.out.println("No students found.");
                return;
            }

            System.out.println("\n========================================");
            System.out.println("     STUDENT DIRECTORY                 ");
            System.out.println("========================================");
            for (Student student : students) {
                System.out.printf("ID: %d | Name: %s %s | Email: %s | GPA: %.2f\n",
                        student.getStudentId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        studentService.calculateGPA(student.getStudentId()));
            }
            System.out.println("========================================");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Helper methods for input
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private Long getLongInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        Long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private Double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        Double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}