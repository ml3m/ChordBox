// src/services/InventoryService.java
package services;

import models.*;
import utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryService {
    public InventoryService() {
        DatabaseUtil.initializeInventoryDatabase();
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO Items (name, price, type) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getInventoryConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setString(3, item.getClass().getSimpleName());
            pstmt.executeUpdate();
            System.out.println("Added item to inventory: " + item.getName());
        } catch (SQLException e) {
            System.err.println("Failed to add item: " + e.getMessage());
        }
    }

    public List<Item> getInventory() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT name, price, type FROM Items";

        try (Connection conn = DatabaseUtil.getInventoryConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String type = rs.getString("type");

                Item item = switch (type) {
                    case "CD" -> new CD(name, price);
                    case "Disk" -> new Disk(name, price);
                    case "Instrument" -> new Instrument(name, price);
                    case "Poster" -> new Poster(name, price);
                    default -> throw new IllegalArgumentException("Unknown type: " + type);
                };
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve inventory: " + e.getMessage());
        }

        return items;
    }

    public Optional<Item> findItemByName(String name) {
        String sql = "SELECT name, price, type FROM Items WHERE name = ?";
        try (Connection conn = DatabaseUtil.getInventoryConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("price");
                String type = rs.getString("type");
                Item item = switch (type) {
                    case "CD" -> new CD(name, price);
                    case "Disk" -> new Disk(name, price);
                    case "Instrument" -> new Instrument(name, price);
                    case "Poster" -> new Poster(name, price);
                    default -> throw new IllegalArgumentException("Unknown type: " + type);
                };
                return Optional.of(item);
            }
        } catch (SQLException e) {
            System.err.println("Failed to find item: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void deleteItem(Item item) {
        String sql = "DELETE FROM Items WHERE name = ?";

        try (Connection conn = DatabaseUtil.getInventoryConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Deleted item from inventory: " + item.getName());
            } else {
                System.out.println("Item not found in inventory: " + item.getName());
            }
        } catch (SQLException e) {
            System.err.println("Failed to delete item: " + e.getMessage());
        }
    }

    public void removeItems(List<Item> items) {
        for (Item item : items) {
            deleteItem(item);
        }
    }

    public boolean checkInventoryAvailability(List<Item> items) {
        for (Item item : items) {
            if (findItemByName(item.getName()).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
