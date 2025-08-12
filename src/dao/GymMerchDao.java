// Ramadan Masadekh
package dao;

import model.Merch;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GymMerchDao {

    // add new merch item
    public void saveItem(Merch m) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "INSERT INTO gym_merch (name, type, price, quantity_in_stock) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getQuantityInStock());

            stmt.executeUpdate();
            System.out.println("Item saved.");
        } catch (Exception e) {
            System.out.println("Error saving item: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // get one item by id
    public Merch getItemById(int itemId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Merch m = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "SELECT item_id, name, type, price, quantity_in_stock FROM gym_merch WHERE item_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                m = new Merch();
                m.setItemId(rs.getInt("item_id"));
                m.setName(rs.getString("name"));
                m.setType(rs.getString("type"));
                m.setPrice(rs.getDouble("price"));
                m.setQuantityInStock(rs.getInt("quantity_in_stock"));
            }
        } catch (Exception e) {
            System.out.println("Error reading item: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
        return m;
    }

    // list all items
    public List<Merch> getAllItems() {
        List<Merch> items = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT item_id, name, type, price, quantity_in_stock FROM gym_merch";
            rs = stmt.executeQuery(sql);

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
            System.out.println("Error reading items: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
        return items;
    }

    // update item
    public void updateItem(Merch m) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "UPDATE gym_merch SET name = ?, type = ?, price = ?, quantity_in_stock = ? WHERE item_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, m.getName());
            stmt.setString(2, m.getType());
            stmt.setDouble(3, m.getPrice());
            stmt.setInt(4, m.getQuantityInStock());
            stmt.setInt(5, m.getItemId());

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Item updated.");
            else System.out.println("No item found with this ID.");
        } catch (Exception e) {
            System.out.println("Error updating item: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // delete item
    public void deleteItem(int itemId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            String sql = "DELETE FROM gym_merch WHERE item_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, itemId);

            int rows = stmt.executeUpdate();
            if (rows > 0) System.out.println("Item deleted.");
            else System.out.println("No item found with this ID.");
        } catch (Exception e) {
            System.out.println("Error deleting item: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // total stock value (price * quantity)
    public double getTotalStockValue() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        double total = 0.0;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/gymdb", "postgres", "admin"
            );

            stmt = conn.createStatement();
            String sql = "SELECT COALESCE(SUM(price * quantity_in_stock), 0) AS total_value FROM gym_merch";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                total = rs.getDouble("total_value");
            }
        } catch (Exception e) {
            System.out.println("Error calculating stock value: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn != null) conn.close(); }
            catch (SQLException ex) { ex.printStackTrace(); }
        }
        return total;
    }
}