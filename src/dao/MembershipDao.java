// Ramadan Masadekh
package dao;

import model.Membership;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipDao {

    // add new membership
    public void saveMembership(Membership membership) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "INSERT INTO memberships (type, description, price, duration_months, member_id, start_date, end_date) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, membership.getType());
            stmt.setString(2, membership.getDescription());
            stmt.setDouble(3, membership.getPrice());
            stmt.setInt(4, membership.getDurationMonths());
            stmt.setInt(5, membership.getMemberId());
            stmt.setString(6, membership.getStartDate());
            stmt.setString(7, membership.getEndDate());

            stmt.executeUpdate();
            System.out.println("Membership saved successfully.");

        } catch (Exception e) {
            System.out.println("Error saving membership: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // list all memberships
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT * FROM memberships";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Membership m = new Membership();
                m.setMembershipId(rs.getInt("membership_id"));
                m.setType(rs.getString("type"));
                m.setDescription(rs.getString("description"));
                m.setPrice(rs.getDouble("price"));
                m.setDurationMonths(rs.getInt("duration_months"));
                m.setMemberId(rs.getInt("member_id"));
                m.setStartDate(rs.getString("start_date"));
                m.setEndDate(rs.getString("end_date"));

                memberships.add(m);
            }

        } catch (Exception e) {
            System.out.println("Error reading memberships: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return memberships;
    }

    // delete membership by id
    public void deleteMembership(int membershipId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "DELETE FROM memberships WHERE membership_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, membershipId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Membership deleted successfully.");
            } else {
                System.out.println("No membership found with this ID.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting membership: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // update membership info
    public void updateMembership(Membership membership) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "UPDATE memberships SET type = ?, description = ?, price = ?, duration_months = ?, " +
                         "member_id = ?, start_date = ?, end_date = ? WHERE membership_id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, membership.getType());
            stmt.setString(2, membership.getDescription());
            stmt.setDouble(3, membership.getPrice());
            stmt.setInt(4, membership.getDurationMonths());
            stmt.setInt(5, membership.getMemberId());
            stmt.setString(6, membership.getStartDate());
            stmt.setString(7, membership.getEndDate());
            stmt.setInt(8, membership.getMembershipId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Membership updated successfully.");
            } else {
                System.out.println("No membership found with this ID.");
            }

        } catch (Exception e) {
            System.out.println("Error updating membership: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}