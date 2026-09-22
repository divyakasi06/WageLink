import java.sql.*;

public class WorkerDAO {

    public void addWorker(Worker worker) throws SQLException {
        String sql = "INSERT INTO workers (name, skill, available_days) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, worker.getName());
            stmt.setString(2, worker.getSkill());
            stmt.setString(3, worker.getAvailableDays());
            stmt.executeUpdate();
        }
    }
        public static void main(String[] args) {
        try {
            WorkerDAO dao = new WorkerDAO();
            Worker w = new Worker("Ramesh Kumar", "Electrician", "Mon-Fri");
            dao.addWorker(w);
            System.out.println("Worker added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}