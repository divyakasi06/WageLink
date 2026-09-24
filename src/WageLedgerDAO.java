import java.sql.*;

public class WorkerDAO {

    public void addWorker(Worker worker) throws SQLException {
        String sql = "INSERT INTO workers (name, skill, available_days, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, worker.getName());
            stmt.setString(2, worker.getSkill());
            stmt.setString(3, worker.getAvailableDays());
            stmt.setString(4, worker.getPhone());
            stmt.executeUpdate();
        }
    }

    // Returns JSON like {"found":true,"id":1,"name":"Ramesh","skill":"Electrician"} or {"found":false}
    public String findByPhoneJson(String phone) throws SQLException {
        String sql = "SELECT id, name, skill FROM workers WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "{\"found\":true,\"id\":" + rs.getInt("id") +
                       ",\"name\":\"" + rs.getString("name").replace("\"", "\\\"") +
                       "\",\"skill\":\"" + rs.getString("skill").replace("\"", "\\\"") + "\"}";
            }
            return "{\"found\":false}";
        }
    }
}