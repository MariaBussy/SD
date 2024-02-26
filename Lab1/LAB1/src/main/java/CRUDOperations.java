import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class CRUDOperations {
    private static Connection connection = null;

    public static void setConnection(String url) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void createTable() throws SQLException {
        try {
            // Create a new table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS students (nume TEXT, prenume TEXT, varsta INTEGER)";
            connection.createStatement().executeUpdate(createTableSQL);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    public static void insertIntoTable(String nume, String prenume, int varsta) throws SQLException {
        try {
            // Insert data into the table
            String insertSQL = "INSERT INTO students (nume, prenume, varsta) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            // Set values for the parameters in the prepared statement
            preparedStatement.setString(1, nume);
            preparedStatement.setString(2, prenume);
            preparedStatement.setInt(3, varsta);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                connection.commit(); // Commit the changes made by the current transaction
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }
    public static void exportDatabaseAsJSON(String outputFilePath) throws IOException, SQLException {
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder jsonBuilder = new StringBuilder();

        try {
            ResultSet rs = executeQuery("SELECT * FROM students");

            while (rs.next()) {
                String[] studentData = {
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        Integer.toString(rs.getInt("varsta"))
                };
                String jsonLine = mapper.writeValueAsString(studentData);
                jsonBuilder.append(jsonLine).append("\n");
            }

            Files.write(Paths.get(outputFilePath), jsonBuilder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private static ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        return ps.executeQuery();
    }
}
