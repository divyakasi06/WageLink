import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class ApiServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Serve the frontend files from the "web" folder
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) path = "/home.html";
            File file = new File("web" + path);
            if (file.exists()) {
                byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
                String contentType = path.endsWith(".css") ? "text/css" :
                                      path.endsWith(".js") ? "application/javascript" : "text/html";
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, bytes.length);
                exchange.getResponseBody().write(bytes);
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
            exchange.close();
        });

        // API: register a worker
        server.createContext("/api/registerWorker", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String name = getParam(body, "name");
            String skill = getParam(body, "skill");
            String availableDays = getParam(body, "availableDays");

            try {
                WorkerDAO dao = new WorkerDAO();
                dao.addWorker(new Worker(name, skill, availableDays));
                sendJson(exchange, 200, "{\"status\":\"success\"}");
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        });
                // API: register an employer
        server.createContext("/api/registerEmployer", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String name = getParam(body, "name");
            String businessType = getParam(body, "businessType");
            String phone = getParam(body, "phone");

            try {
                EmployerDAO dao = new EmployerDAO();
                dao.addEmployer(new Employer(name, businessType, phone));
                sendJson(exchange, 200, "{\"status\":\"success\"}");
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        });
                // API: post a job
        server.createContext("/api/postJob", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String title = getParam(body, "title");
            String location = getParam(body, "location");
            int wage = Integer.parseInt(getParam(body, "wage"));
            String jobDate = getParam(body, "jobDate");
            String postedBy = getParam(body, "postedBy");

            try {
                JobDAO dao = new JobDAO();
                dao.addJob(new Job(title, location, wage, jobDate, postedBy));
                sendJson(exchange, 200, "{\"status\":\"success\"}");
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        });
                // API: employer confirms a job is done and payment made
        server.createContext("/api/confirmJob", exchange -> {
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1);
                return;
            }
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            int workerId = Integer.parseInt(getParam(body, "workerId"));
            int jobId = Integer.parseInt(getParam(body, "jobId"));
            int amountPaid = Integer.parseInt(getParam(body, "amountPaid"));
            String completedDate = getParam(body, "completedDate");

            try {
                WageLedgerDAO dao = new WageLedgerDAO();
                dao.logPayment(workerId, jobId, amountPaid, completedDate);
                sendJson(exchange, 200, "{\"status\":\"success\"}");
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        });
        // API: get work history for a worker
        server.createContext("/api/workHistory", exchange -> {
            String query = exchange.getRequestURI().getQuery(); // e.g. "workerId=1"
            int workerId = Integer.parseInt(getParam(query, "workerId"));

            try {
                WageLedgerDAO dao = new WageLedgerDAO();
                String json = dao.getWorkHistoryJson(workerId);
                sendJson(exchange, 200, json);
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        });
                // API: get mock credit score for a worker
        server.createContext("/api/creditScore", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            int workerId = Integer.parseInt(getParam(query, "workerId"));

            try {
                WageLedgerDAO dao = new WageLedgerDAO();
                String json = dao.getCreditScoreJson(workerId);
                sendJson(exchange, 200, json);
            } catch (SQLException e) {
                sendJson(exchange, 500, "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
            }
        }); 
        server.setExecutor(null);
        server.start();
        System.out.println("Server running at http://localhost:8080");
    }

    private static String getParam(String body, String key) {
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2 && kv[0].equals(key)) {
                return java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    private static void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}