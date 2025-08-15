// Ramadan Masadekh
package dao;

import model.Member;
import util.DBConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemberDao {

    private static final Logger logger = Logger.getLogger(MemberDao.class.getName());

    // Add new member
    public void addMember(Member m) {
        String sql = "INSERT INTO members (name, email, phone, address, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEmail());
            stmt.setString(3, m.getPhoneNumber());
            stmt.setString(4, m.getAddress());
            stmt.setString(5, m.getPasswordHash());
            stmt.setString(6, "Member");

            stmt.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error adding member", e);
        }
    }

    // Update member
    public void updateMember(Member m) {
        String sql = "UPDATE members SET name = ?, email = ?, phone = ?, address = ? WHERE member_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEmail());
            stmt.setString(3, m.getPhoneNumber());
            stmt.setString(4, m.getAddress());
            stmt.setInt(5, m.getUserId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Member updated.");
            } else {
                System.out.println("No member found with this ID.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating member", e);
        }
    }

    // Display all members
    public void displayAllMembers() {
        String sql = "SELECT * FROM members";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                System.out.println(
                        rs.getInt("member_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email") + " | " +
                                rs.getString("phone") + " | " +
                                rs.getString("address")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error displaying members", e);
        }
    }
}
