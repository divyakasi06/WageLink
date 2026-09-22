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
}