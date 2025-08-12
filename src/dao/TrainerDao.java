package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import model.Trainer;

public class TrainerDao {

    // Add a new trainer
    public void addTrainer(Trainer t) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "INSERT INTO trainers (name, email, phone, address, specialty) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, t.getName());
            stmt.setString(2, t.getEmail());
            stmt.setString(3, t.getPhoneNumber());
            stmt.setString(4, t.getAddress());


            stmt.executeUpdate();
            System.out.println("Trainer added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // Update trainer details
    public void updateTrainer(Trainer t) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "UPDATE trainers SET name = ?, email = ?, phone = ?, address = ?, specialty = ? WHERE trainer_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, t.getName());
            stmt.setString(2, t.getEmail());
            stmt.setString(3, t.getPhoneNumber());
            stmt.setString(4, t.getAddress());
            stmt.setInt(6, t.getTrainerId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Trainer updated.");
            } else {
                System.out.println("No trainer found with this ID.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // Display all trainers
    public void displayAllTrainers() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT * FROM trainers";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                    rs.getInt("trainer_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone") + " | " +
                    rs.getString("address") + " | " +
                    rs.getString("specialty")
                );
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}