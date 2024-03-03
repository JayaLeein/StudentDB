package org.cst8288Lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public void insert(Student student) throws SQLException {
        String sql = "INSERT INTO Student (studentId, firstName, lastName) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.executeUpdate();
        }
    }

    public Student findById(int studentId) throws SQLException {
        String sql = "SELECT * FROM Student WHERE studentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractStudentFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = extractStudentFromResultSet(resultSet);
                students.add(student);
            }
        }
        return students;
    }

    public void update(Student student) throws SQLException {
        String sql = "UPDATE Student SET firstName = ?, lastName = ? WHERE studentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getStudentId());
            statement.executeUpdate();
        }
    }

    public void delete(int studentId) throws SQLException {
        String sql = "DELETE FROM Student WHERE studentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
    }

    private Student extractStudentFromResultSet(ResultSet resultSet) throws SQLException {
        int studentId = resultSet.getInt("studentId");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        return new Student(studentId, firstName, lastName);
    }
}

