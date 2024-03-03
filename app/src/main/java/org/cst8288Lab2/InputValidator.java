package org.cst8288Lab2;

import java.util.regex.Pattern;

public class InputValidator {
    
    // Regular expressions for validation
    private static final String STUDENT_ID_REGEX = "\\d{9}";
    private static final String COURSE_ID_REGEX = "[a-zA-Z]{3}\\d{4}[A-Z]?";
    private static final String TERM_REGEX = "(WINTER|SUMMER|FALL)";
    private static final String YEAR_REGEX = "\\d{4}";
    
    // Minimum year allowed
    private static final int MIN_YEAR = 1965;

    // Validate student ID
    public static boolean isValidStudentId(String studentId) {
        return Pattern.matches(STUDENT_ID_REGEX, studentId);
    }

    // Validate course ID
    public static boolean isValidCourseId(String courseId) {
        return Pattern.matches(COURSE_ID_REGEX, courseId);
    }

    // Validate term
    public static boolean isValidTerm(String term) {
        return Pattern.matches(TERM_REGEX, term.toUpperCase());
    }

    // Validate year
    public static boolean isValidYear(String year) {
        if (!Pattern.matches(YEAR_REGEX, year)) {
            return false;
        }
        int numericYear = Integer.parseInt(year);
        return numericYear >= MIN_YEAR;
    }
}

