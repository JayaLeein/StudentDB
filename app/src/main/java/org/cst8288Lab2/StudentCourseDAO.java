package org.cst8288Lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentCourseDAO {
    private Connection connection;

    public StudentCourseDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(StudentCourse studentCourse) throws SQLException {
        String sql = "INSERT INTO StudentCourse (studentId, courseId, term, year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentCourse.getStudentId());
            statement.setString(2, studentCourse.getCourseId());
            statement.setInt(3, studentCourse.getTerm());
            statement.setInt(4, studentCourse.getYear());
            statement.executeUpdate();
        }
    }

    public List<StudentCourse> findByStudentId(int studentId) throws SQLException {
        List<StudentCourse> studentCourses = new ArrayList<>();
        String sql = "SELECT * FROM StudentCourse WHERE studentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    StudentCourse studentCourse = extractStudentCourseFromResultSet(resultSet);
                    studentCourses.add(studentCourse);
                }
            }
        }
        return studentCourses;
    }

    public List<StudentCourse> findByCourseId(String courseId) throws SQLException {
        List<StudentCourse> studentCourses = new ArrayList<>();
        String sql = "SELECT * FROM StudentCourse WHERE courseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    StudentCourse studentCourse = extractStudentCourseFromResultSet(resultSet);
                    studentCourses.add(studentCourse);
                }
            }
        }
        return studentCourses;
    }

    // Add other CRUD operations as needed

    private StudentCourse extractStudentCourseFromResultSet(ResultSet resultSet) throws SQLException {
        int studentId = resultSet.getInt("studentId");
        String courseId = resultSet.getString("courseId");
        int term = resultSet.getInt("term");
        int year = resultSet.getInt("year");
        return new StudentCourse(studentId, courseId, term, year);
    }
}
