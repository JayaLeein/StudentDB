package org.cst8288Lab2;

public class ExampleUsage {
    public static void main(String[] args) {
        // Example usage of input validation
        String studentId = "123456789";
        String courseId = "CST8288A";
        String term = "WINTER";
        String year = "2024";

        if (InputValidator.isValidStudentId(studentId)) {
            System.out.println("Valid student ID");
        } else {
            System.out.println("Invalid student ID");
        }

        if (InputValidator.isValidCourseId(courseId)) {
            System.out.println("Valid course ID");
        } else {
            System.out.println("Invalid course ID");
        }

        if (InputValidator.isValidTerm(term)) {
            System.out.println("Valid term");
        } else {
            System.out.println("Invalid term");
        }

        if (InputValidator.isValidYear(year)) {
            System.out.println("Valid year");
        } else {
            System.out.println("Invalid year");
        }
    }
}

