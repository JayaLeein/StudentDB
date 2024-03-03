package org.cst8288Lab2;

public class StudentCourse {
    private int studentId;
    private String courseId;
    private int term;
    private int year;

    public StudentCourse(int studentId, String courseId, int term, int year) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.term = term;
        this.year = year;
    }

    // Getters and setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

