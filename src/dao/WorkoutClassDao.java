// Ramadan Masadekh
package dao;

import model.WorkoutClass;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkoutClassDao {

    private static final Logger logger = Logger.getLogger(WorkoutClassDao.class.getName());

    // add new workout class
    public void saveClass(WorkoutClass c) {
        String sql = "INSERT INTO workout_classes (title, description, schedule, trainer_id) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, c.getTitle());
            stmt.setString(2, c.getDescription());
            stmt.setString(3, c.getSchedule());
            stmt.setInt(4, c.getTrainerId());

            stmt.executeUpdate();
            System.out.println("Class saved successfully.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving class", e);
        }
    }

    // get a class by id
    public WorkoutClass getClassById(int classId) {
        WorkoutClass c = null;
        String sql = "SELECT * FROM workout_classes WHERE class_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, classId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    c = new WorkoutClass();
                    c.setClassId(rs.getInt("class_id"));
                    c.setTitle(rs.getString("title"));
                    c.setDescription(rs.getString("description"));
                    c.setSchedule(rs.getString("schedule"));
                    c.setTrainerId(rs.getInt("trainer_id"));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading class", e);
        }

        return c;
    }

    // list all classes
    public List<WorkoutClass> getAllClasses() {
        List<WorkoutClass> classes = new ArrayList<>();
        String sql = "SELECT * FROM workout_classes";

        try (
                Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                WorkoutClass c = new WorkoutClass();
                c.setClassId(rs.getInt("class_id"));
                c.setTitle(rs.getString("title"));
                c.setDescription(rs.getString("description"));
                c.setSchedule(rs.getString("schedule"));
                c.setTrainerId(rs.getInt("trainer_id"));
                classes.add(c);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading classes", e);
        }

        return classes;
    }

    // update a class
    public void updateClass(WorkoutClass c) {
        String sql = "UPDATE workout_classes SET title = ?, description = ?, schedule = ?, trainer_id = ? WHERE class_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating class", e);
        }
    }

    // delete a class
    public void deleteClass(int classId) {
        String sql = "DELETE FROM workout_classes WHERE class_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, classId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Class deleted successfully.");
            } else {
                System.out.println("No class found with this ID.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting class", e);
        }
    }
}
