package dao;

import model.Merch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GymMerchDao {
    private static final Logger logger = Logger.getLogger(GymMerchDao.class.getName());

    // save item
    public void saveItem(Merch m) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO gym_merch (name, type, price, quantity_in_stock) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getQuantityInStock());

            stmt.executeUpdate();
            System.out.println("Item saved successfully.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving item", e);
        }
    }

    // list all items
    public List<Merch> getAllItems() {
        List<Merch> items = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM gym_merch")) {

            while (rs.next()) {
                Merch m = new Merch();
                m.setItemId(rs.getInt("item_id"));
                m.setName(rs.getString("name"));
                m.setType(rs.getString("type"));
                m.setPrice(rs.getDouble("price"));
                m.setQuantityInStock(rs.getInt("quantity_in_stock"));
                items.add(m);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error reading merch", e);
        }

        return items;
    }

    // delete item
    public void deleteItem(int itemId) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin");
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM gym_merch WHERE item_id = ?")) {

            stmt.setInt(1, itemId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with this ID.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting item", e);
        }
    }

    // update item
    public void updateItem(Merch m) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin");
             PreparedStatement stmt = conn.prepareStatement("UPDATE gym_merch SET name = ?, type = ?, price = ?, quantity_in_stock = ? WHERE item_id = ?")) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getQuantityInStock());
            stmt.setInt(5, m.getItemId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("No item found with this ID.");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating item", e);
        }
    }

    // get total stock value (price * quantity)
    public double getTotalStockValue() {
        double total = 0;

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(price * quantity_in_stock) AS total_value FROM gym_merch")) {

            if (rs.next()) {
                total = rs.getDouble("total_value");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error calculating total stock value", e);
        }

        return total;
    }
}
