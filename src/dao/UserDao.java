// Ramadan Masadekh
package dao;

import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao {

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    // Add new user
    public void saveUser(User user) {
        String sql = "INSERT INTO users (name, email, phone_number, address, password_hash, role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhoneNumber());
            stmt.setString(4, user.getAddress());
            stmt.setString(5, user.getPasswordHash());
            stmt.setString(6, user.getRole());

            stmt.executeUpdate();
            System.out.println("User saved successfully.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving user", e);
        }
    }

    // List all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhoneNumber(rs.getString("phone_number"));
                u.setAddress(rs.getString("address"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRole(rs.getString("role"));

                users.add(u);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading users", e);
        }

        return users;
    }

    // Find user by email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRole(rs.getString("role"));
                u.setAddress(rs.getString("address"));
                u.setPhoneNumber(rs.getString("phone_number"));
                return u;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error finding user by email", e);
        }

        return null;
    }

    // Delete user by ID
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user found with this ID.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting user", e);
        }
    }
}
