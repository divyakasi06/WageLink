import java.sql.*;

public class EmployerDAO {

    public void addEmployer(Employer employer) throws SQLException {
        String sql = "INSERT INTO employers (name, business_type, phone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employer.getName());
            stmt.setString(2, employer.getBusinessType());
            stmt.setString(3, employer.getPhone());
            stmt.executeUpdate();
        }
    }
}