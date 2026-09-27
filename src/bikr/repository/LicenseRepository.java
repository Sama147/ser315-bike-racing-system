package bikr.repository;

import bikr.config.DatabaseConfig;
import bikr.model.License;
import bikr.model.enums.CategoryLevel;

import java.sql.*;
import java.time.LocalDate;

public class LicenseRepository {

    public int insert(License license) {
        String sql = "INSERT INTO licenses (user_id, expiration_date, category) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, license.getUserId());
            ps.setString(2, license.getExpirationDate().toString());
            ps.setString(3, license.getCategory().name());
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                license.setLicenseId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert license: " + e.getMessage(), e);
        }
    }

    public License findByUserId(int userId) {
        String sql = "SELECT * FROM licenses WHERE user_id = ? ORDER BY license_id DESC LIMIT 1";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                License l = new License();
                l.setLicenseId(rs.getInt("license_id"));
                l.setUserId(rs.getInt("user_id"));
                l.setExpirationDate(LocalDate.parse(rs.getString("expiration_date")));
                l.setCategory(CategoryLevel.valueOf(rs.getString("category")));
                return l;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find license: " + e.getMessage(), e);
        }
    }

    public void updateCategory(int userId, CategoryLevel newCategory) {
        String sql = "UPDATE licenses SET category = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newCategory.name());
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update license category: " + e.getMessage(), e);
        }
    }
}