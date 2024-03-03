package org.cst8288Lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Course course) throws SQLException {
        String sql = "INSERT INTO Course (courseId, courseName) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            statement.executeUpdate();
        }
    }

    public Course findById(String courseId) throws SQLException {
        String sql = "SELECT * FROM Course WHERE courseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCourseFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Course> findAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = extractCourseFromResultSet(resultSet);
                courses.add(course);
            }
        }
        return courses;
    }

    public void update(Course course) throws SQLException {
        String sql = "UPDATE Course SET courseName = ? WHERE courseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseId());
            statement.executeUpdate();
        }
    }

    public void delete(String courseId) throws SQLException {
        String sql = "DELETE FROM Course WHERE courseId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseId);
            statement.executeUpdate();
        }
    }

    private Course extractCourseFromResultSet(ResultSet resultSet) throws SQLException {
        String courseId = resultSet.getString("courseId");
        String courseName = resultSet.getString("courseName");
        return new Course(courseId, courseName);
    }
}

