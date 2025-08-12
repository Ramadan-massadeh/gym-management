// Ramadan Masadekh
package dao;

import model.WorkoutClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkoutClassDao {

    // add new workout class
    public void saveClass(WorkoutClass c) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "INSERT INTO workout_classes (title, description, schedule, trainer_id) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getTitle());
            stmt.setString(2, c.getDescription());
            stmt.setString(3, c.getSchedule());
            stmt.setInt(4, c.getTrainerId());

            stmt.executeUpdate();
            System.out.println("Class saved successfully.");
        } catch (Exception e) {
            System.out.println("Error saving class: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // get a class by id
    public WorkoutClass getClassById(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        WorkoutClass c = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "SELECT * FROM workout_classes WHERE class_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                c = new WorkoutClass();
                c.setClassId(rs.getInt("class_id"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setSchedule(rs.getString("schedule"));
                c.setTrainerId(rs.getInt("trainer_id"));
            }

        } catch (Exception e) {
            System.out.println("Error reading class: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return c;
    }

    // list all classes
    public List<WorkoutClass> getAllClasses() {
        List<WorkoutClass> classes = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT * FROM workout_classes";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                WorkoutClass c = new WorkoutClass();
                c.setClassId(rs.getInt("class_id"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setSchedule(rs.getString("schedule"));
                c.setTrainerId(rs.getInt("trainer_id"));
                classes.add(c);
            }

        } catch (Exception e) {
            System.out.println("Error reading classes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return classes;
    }

    // update a class
    public void updateClass(WorkoutClass c) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "UPDATE workout_classes SET title = ?, description = ?, schedule = ?, trainer_id = ? WHERE class_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, c.getTitle());
            stmt.setString(2, c.getDescription());
            stmt.setString(3, c.getSchedule());
            stmt.setInt(4, c.getTrainerId());
            stmt.setInt(5, c.getClassId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Class updated successfully.");
            } else {
                System.out.println("No class found with this ID.");
            }

        } catch (Exception e) {
            System.out.println("Error updating class: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // delete a class
    public void deleteClass(int classId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "DELETE FROM workout_classes WHERE class_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, classId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Class deleted successfully.");
            } else {
                System.out.println("No class found with this ID.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting class: " + e.getMessage());
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