// StudentGradeManagementApplication.java
package com.student_grade_management;

import com.student_grade_management.controller.StudentGradeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StudentGradeManagementApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StudentGradeManagementApplication.class, args);

		// Get the controller bean
		StudentGradeController controller = context.getBean(StudentGradeController.class);

		// Start the console application
		controller.startConsoleApp();
	}
}