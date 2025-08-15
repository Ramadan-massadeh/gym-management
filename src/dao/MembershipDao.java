// Ramadan Masadekh
package dao;

import model.Membership;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembershipDao {

    private static final Logger logger = Logger.getLogger(MembershipDao.class.getName());

    // add new membership
    public void saveMembership(Membership membership) {
        String sql = "INSERT INTO memberships (type, description, price, duration_months, member_id, start_date, end_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, membership.getType());
            stmt.setString(2, membership.getDescription());
            stmt.setDouble(3, membership.getPrice());
            stmt.setInt(4, membership.getDurationMonths());
            stmt.setInt(5, membership.getMemberId());
            stmt.setString(6, membership.getStartDate());
            stmt.setString(7, membership.getEndDate());

            stmt.executeUpdate();
            System.out.println("Membership saved successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving membership", e);
        }
    }

    // list all memberships
    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading memberships", e);
        }

        return memberships;
    }

    // get memberships by member ID
    public List<Membership> getMembershipsByMemberId(int memberId) {
        List<Membership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE member_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

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

            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching memberships by member ID", e);
        }

        return memberships;
    }

    // delete membership by id
    public void deleteMembership(int membershipId) {
        String sql = "DELETE FROM memberships WHERE membership_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, membershipId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Membership deleted successfully.");
            } else {
                System.out.println("No membership found with this ID.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting membership", e);
        }
    }

    // update membership info
    public void updateMembership(Membership membership) {
        String sql = "UPDATE memberships SET type = ?, description = ?, price = ?, duration_months = ?, " +
                "member_id = ?, start_date = ?, end_date = ? WHERE membership_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating membership", e);
        }
    }
}
