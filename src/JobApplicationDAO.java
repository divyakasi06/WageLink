import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDAO {

    public void applyToJob(int jobId, int workerId, String appliedDate) throws SQLException {
        String sql = "INSERT INTO job_applications (job_id, worker_id, applied_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jobId);
            stmt.setInt(2, workerId);
            stmt.setString(3, appliedDate);
            stmt.executeUpdate();
        }
    }

    // Returns a JSON array of applicants for a given job: [{"workerId":1,"name":"Ramesh","skill":"Electrician"}, ...]
    public String getApplicantsJson(int jobId) throws SQLException {
        String sql = "SELECT w.id AS worker_id, w.name, w.skill FROM job_applications ja " +
                     "JOIN workers w ON ja.worker_id = w.id WHERE ja.job_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, jobId);
            ResultSet rs = stmt.executeQuery();
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{\"workerId\":").append(rs.getInt("worker_id"))
                    .append(",\"name\":\"").append(rs.getString("name").replace("\"", "\\\""))
                    .append("\",\"skill\":\"").append(rs.getString("skill").replace("\"", "\\\""))
                    .append("\"}");
                first = false;
            }
            json.append("]");
            return json.toString();
        }
    }
}