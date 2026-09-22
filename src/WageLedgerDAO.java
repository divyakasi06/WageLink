import java.sql.*;

public class WageLedgerDAO {

    public void logPayment(int workerId, int jobId, int amountPaid, String completedDate) throws SQLException {
        String sql = "INSERT INTO wage_ledger (worker_id, job_id, amount_paid, completed_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, workerId);
            stmt.setInt(2, jobId);
            stmt.setInt(3, amountPaid);
            stmt.setString(4, completedDate);
            stmt.executeUpdate();
        }
    }

    // This is the key feature: total earnings + job count for a worker's "work history"
    public void printWorkHistory(int workerId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total_jobs, SUM(amount_paid) AS total_earned FROM wage_ledger WHERE worker_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int totalJobs = rs.getInt("total_jobs");
                int totalEarned = rs.getInt("total_earned");
                System.out.println("Total jobs completed: " + totalJobs);
                System.out.println("Total earned: " + totalEarned);
            }
        }
    }

    public static void main(String[] args) {
        try {
            WageLedgerDAO dao = new WageLedgerDAO();
            // worker_id=1 (Ramesh Kumar), job_id=1 (House Painting), paid 800, completed today
            dao.logPayment(1, 1, 800, "2026-09-21");
            System.out.println("Payment logged successfully!");

            dao.printWorkHistory(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public String getWorkHistoryJson(int workerId) throws SQLException {
        String sql = "SELECT COUNT(*) AS total_jobs, SUM(amount_paid) AS total_earned FROM wage_ledger WHERE worker_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, workerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int totalJobs = rs.getInt("total_jobs");
                int totalEarned = rs.getInt("total_earned");
                return "{\"totalJobs\":" + totalJobs + ",\"totalEarned\":" + totalEarned + "}";
            }
            return "{\"totalJobs\":0,\"totalEarned\":0}";
        }
    }
}