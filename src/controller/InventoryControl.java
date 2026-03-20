package controller;

import model.DbConnection;
import Interface.Inventory.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class InventoryControl {

    /**
     * Fetches all items from the database.
     */
    public ObservableList<Item> getAllInventory() {
        ObservableList<Item> list = FXCollections.observableArrayList();
        String query = "SELECT item_name, stock FROM inventory";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Item(
                    rs.getString("item_name"),
                    rs.getString("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates the stock count for a specific item.
     */
    public boolean updateStock(String itemName, String newStock) {
        String query = "UPDATE inventory SET stock = ? WHERE item_name = ?";
        
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, newStock);
            ps.setString(2, itemName);
            
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a new item to the inventory.
     */
    public void addItem(String name, String stock) {
        String query = "INSERT INTO inventory (item_name, stock) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, stock);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}