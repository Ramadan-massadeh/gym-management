package dao;

import model.Merch;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class GymMerchDao {
    private static final Logger logger = Logger.getLogger(GymMerchDao.class.getName());

    // save item
    public void saveItem(Merch m) {
        String sql = "INSERT INTO gym_merch (name, type, price, quantity_in_stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getQuantityInStock());

            stmt.executeUpdate();
            System.out.println("Item saved successfully.");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving item", e);
        }
    }

    // list all items
    public List<Merch> getAllItems() {
        List<Merch> items = new ArrayList<>();
        String sql = "SELECT * FROM gym_merch";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Merch m = new Merch();
                m.setItemId(rs.getInt("item_id"));
                m.setName(rs.getString("name"));
                m.setType(rs.getString("type"));
                m.setPrice(rs.getDouble("price"));
                m.setQuantityInStock(rs.getInt("quantity_in_stock"));
                items.add(m);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error reading merch", e);
        }

        return items;
    }

    // delete item
    public void deleteItem(int itemId) {
        String sql = "DELETE FROM gym_merch WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with this ID.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting item", e);
        }
    }

    // update item
    public void updateItem(Merch m) {
        String sql = "UPDATE gym_merch SET name = ?, type = ?, price = ?, quantity_in_stock = ? WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating item", e);
        }
    }

    // get total stock value (price * quantity)
    public double getTotalStockValue() {
        double total = 0;
        String sql = "SELECT SUM(price * quantity_in_stock) AS total_value FROM gym_merch";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                total = rs.getDouble("total_value");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error calculating total stock value", e);
        }

        return total;
    }

    // get item by ID
    public Merch getItemById(int itemId) {
        Merch item = null;
        String sql = "SELECT * FROM gym_merch WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                item = new Merch();
                item.setItemId(rs.getInt("item_id"));
                item.setName(rs.getString("name"));
                item.setType(rs.getString("type"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantityInStock(rs.getInt("quantity_in_stock"));
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving item with ID: " + itemId, e);
        }

        return item;
    }
}
