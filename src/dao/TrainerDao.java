package dao;

import model.Trainer;
import util.DBConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainerDao {

    private static final Logger logger = Logger.getLogger(TrainerDao.class.getName());

    // Add a new trainer
    public void addTrainer(Trainer t) {
        String sql = "INSERT INTO trainers (name, email, phone, address) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, t.getName());
            stmt.setString(2, t.getEmail());
            stmt.setString(3, t.getPhoneNumber());
            stmt.setString(4, t.getAddress());

            stmt.executeUpdate();
            System.out.println("Trainer added successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding trainer", e);
        }
    }

    // Update trainer details
    public void updateTrainer(Trainer t) {
        String sql = "UPDATE trainers SET name = ?, email = ?, phone = ?, address = ? WHERE trainer_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, t.getName());
            stmt.setString(2, t.getEmail());
            stmt.setString(3, t.getPhoneNumber());
            stmt.setString(4, t.getAddress());
            stmt.setInt(5, t.getTrainerId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Trainer updated.");
            } else {
                System.out.println("No trainer found with this ID.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating trainer", e);
        }
    }

    // Display all trainers
    public void displayAllTrainers() {
        String sql = "SELECT * FROM trainers";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt("trainer_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email") + " | " +
                                rs.getString("phone") + " | " +
                                rs.getString("address")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error displaying trainers", e);
        }
    }
}
