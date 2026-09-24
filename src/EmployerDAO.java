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
        // Returns JSON like {"found":true,"id":1,"name":"Kasi","businessType":"Household"} or {"found":false}
    public String findByPhoneJson(String phone) throws SQLException {
        String sql = "SELECT id, name, business_type FROM employers WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "{\"found\":true,\"id\":" + rs.getInt("id") +
                       ",\"name\":\"" + rs.getString("name").replace("\"", "\\\"") +
                       "\",\"businessType\":\"" + rs.getString("business_type").replace("\"", "\\\"") + "\"}";
            }
            return "{\"found\":false}";
        }
    }
}