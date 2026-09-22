import java.sql.*;

public class JobDAO {

    public void addJob(Job job) throws SQLException {
        String sql = "INSERT INTO jobs (title, location, wage, job_date, posted_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, job.getTitle());
            stmt.setString(2, job.getLocation());
            stmt.setInt(3, job.getWage());
            stmt.setString(4, job.getJobDate());
            stmt.setString(5, job.getPostedBy());
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        try {
            JobDAO dao = new JobDAO();
            Job j = new Job("House Painting", "Whitefield, Bangalore", 800, "2026-09-25", "Priya Sharma");
            dao.addJob(j);
            System.out.println("Job added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // Returns a JSON array of all posted jobs: [{"id":1,"title":"...","location":"...","wage":800,"jobDate":"...","postedBy":"..."}]
    public String getAllJobsJson() throws SQLException {
        String sql = "SELECT id, title, location, wage, job_date, posted_by FROM jobs ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            StringBuilder json = new StringBuilder("[");
            boolean first = true;
            while (rs.next()) {
                if (!first) json.append(",");
                json.append("{\"id\":").append(rs.getInt("id"))
                    .append(",\"title\":\"").append(rs.getString("title").replace("\"", "\\\""))
                    .append("\",\"location\":\"").append(rs.getString("location").replace("\"", "\\\""))
                    .append("\",\"wage\":").append(rs.getInt("wage"))
                    .append(",\"jobDate\":\"").append(rs.getDate("job_date"))
                    .append("\",\"postedBy\":\"").append(rs.getString("posted_by").replace("\"", "\\\""))
                    .append("\"}");
                first = false;
            }
            json.append("]");
            return json.toString();
        }
    }
}