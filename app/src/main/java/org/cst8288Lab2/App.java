/*
 * Main
 */
package org.cst8288Lab2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * App
 */
public class App {
    /**
     * Parses the file: bulk-import.csv
     * Validates each item in each row and updates the database accordingly.
     * @param args - 
     */
    public static void main(String[] args) {
    	
        // Ensure to use the Properties class to load values from the database.properties file
        Properties dbConnection = new Properties();
        
        // Preserve this input path
        try (InputStream in = new FileInputStream("./app/data/database.properties.cfg")) {
            dbConnection.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return; // Terminate execution if database properties cannot be loaded
        }
        
        // Load the MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return; // Terminate execution if the JDBC driver cannot be loaded
        }
        
        // Establish database connection
        try (Connection connection = DriverManager.getConnection(buildConnectionString(dbConnection), 
            dbConnection.getProperty("jdbc.username"), dbConnection.getProperty("jdbc.password"))) {
            // Read and validate each line from the CSV file, and update the database accordingly
            validateAndInsertData(connection, "./app/data/bulk-import.csv");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to build database connection string
    private static String buildConnectionString(Properties dbProperties) {
        String dbType = dbProperties.getProperty("db");
        String dbName = dbProperties.getProperty("name");
        String host = dbProperties.getProperty("host");
        String port = dbProperties.getProperty("port");
        String user = dbProperties.getProperty("user");
        String pass = dbProperties.getProperty("pass");
        return "jdbc:" + dbType + "://" + host + ":" + port + "/" + dbName + "?user=" + user + "&password=" + pass;
    }
    
    // Method to read and validate each line from the CSV file, and update the database accordingly
    private static void validateAndInsertData(Connection connection, String filePath) throws IOException, SQLException {
        try (InputStream in = new FileInputStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String line;
            // Skip the header line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the CSV line into individual data elements
                String[] data = line.split(",");
                
                // Validate each data element
                boolean isValid = true;
                for (int i = 0; i < data.length; i++) {
                    if (!isValid(data[i], i)) { // Pass the index along with the data
                        isValid = false;
                        break;
                    }
                }
                
                // If all elements are valid, insert data into the database
                if (isValid) {
                    insertDataIntoDatabase(connection, data);
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }
        }
    }

    
    // Method to validate data element
    private static boolean isValid(String data, int index) {
        try {
            switch (index) {
                case 0: // studentId
                    return data.matches("\\d{9}");
                case 3: // courseId
//                    return data.matches("[a-zA-Z]{3}\\d{4}");
                    boolean courseIdValid = data.matches("[a-zA-Z]{3}\\d{4}");
                    System.out.println("Course ID validation result: " + courseIdValid);
                    return courseIdValid;
                case 5: // term
                    return data.equalsIgnoreCase("WINTER") || data.equalsIgnoreCase("SUMMER") || data.equalsIgnoreCase("FALL");
                case 6: // year
                    int year = Integer.parseInt(data);
                    return year >= 1965 && data.length() == 4;
                default:
                    return true; // No validation for other columns
            }
        } catch (NumberFormatException e) {
            return false; // Invalid year format
        }
    }

    // Method to insert data into the database
    private static void insertDataIntoDatabase(Connection connection, String[] data) throws SQLException {
        String tableName = getTableName(data.length); // Determine table name based on number of columns
        String sql = getInsertStatement(tableName, data.length); // Get insert SQL statement
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]); // Set parameters for the prepared statement
            }
            statement.executeUpdate(); // Execute the insert statement
        }
    }

    // Method to determine table name based on number of columns
    private static String getTableName(int columnCount) {
        switch (columnCount) {
            case 3:
                return "Student";
            case 6:
                return "Course";
            case 7:
                return "StudentCourse";
            default:
                throw new IllegalArgumentException("Invalid number of columns");
        }
    }

    // Method to get insert SQL statement based on table name and number of columns
    private static String getInsertStatement(String tableName, int columnCount) {
        switch (tableName) {
            case "Student":
                return "INSERT INTO Student (studentId, firstName, lastName) VALUES (?, ?, ?)";
            case "Course":
                return "INSERT INTO Course (courseId, courseName) VALUES (?, ?)";
            case "StudentCourse":
                return "INSERT INTO StudentCourse (studentId, courseId, term, year) VALUES (?, ?, ?, ?)";
            default:
                throw new IllegalArgumentException("Invalid table name");
        }
    }

}


