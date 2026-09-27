package bikr.repository;

import bikr.config.DatabaseConfig;
import bikr.model.Registration;
import bikr.model.enums.CategoryLevel;
import bikr.model.enums.RegistrationStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationRepository {

    public int insert(Registration reg) {
        String sql = "INSERT INTO registrations (racer_id, race_id, status, category) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reg.getRacerId());
            ps.setInt(2, reg.getRaceId());
            ps.setString(3, reg.getStatus().name());
            ps.setString(4, reg.getCategory().name());
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                reg.setRegistrationId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert registration: " + e.getMessage(), e);
        }
    }

    public int countByRace(int raceId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE race_id = ? AND status = 'CONFIRMED'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, raceId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count registrations: " + e.getMessage(), e);
        }
    }

    public List<Registration> findByRace(int raceId) {
        String sql = "SELECT * FROM registrations WHERE race_id = ?";
        List<Registration> list = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, raceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration r = new Registration();
                    r.setRegistrationId(rs.getInt("registration_id"));
                    r.setRacerId(rs.getInt("racer_id"));
                    r.setRaceId(rs.getInt("race_id"));
                    r.setStatus(RegistrationStatus.valueOf(rs.getString("status")));
                    r.setCategory(CategoryLevel.valueOf(rs.getString("category")));
                    list.add(r);
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find registrations: " + e.getMessage(), e);
        }
    }
}