// Ramadan Masadekh
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import model.Member;

public class MemberDao {

    // Add new member
    public void addMember(Member m) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "INSERT INTO members (name, email, phone, address, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getEmail());
            stmt.setString(3, m.getPhoneNumber());
            stmt.setString(4, m.getAddress());
            stmt.setString(5, m.getPasswordHash());
            stmt.setString(6, "Member");

            stmt.executeUpdate();
            System.out.println("Member added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); } 
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // update member
    public void updateMember(Member m) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "UPDATE members SET name = ?, email = ?, phone = ?, address = ? WHERE member_id = ?";
            stmt = conn.prepareStatement(sql);
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); } 
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    //Display All members
    public void displayAllMembers() {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT * FROM members";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println(
                    rs.getInt("member_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("email") + " | " +
                    rs.getString("phone") + " | " +
                    rs.getString("address")
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