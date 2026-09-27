package bikr.repository;

import bikr.config.DatabaseConfig;
import bikr.model.RaceResult;
import bikr.model.ResultEntry;

import java.sql.*;

public class RaceResultRepository {

    public int insertResult(RaceResult result) {
        String sql = "INSERT INTO race_results (race_id, category, posted_date) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, result.getRaceId());
            ps.setString(2, result.getCategory().name());
            ps.setString(3, result.getPostedDate().toString());
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                result.setResultId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert race result: " + e.getMessage(), e);
        }
    }

    public int insertEntry(ResultEntry entry) {
        String sql = "INSERT INTO result_entries (result_id, racer_id, finishing_position, podium_counted) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, entry.getResultId());
            ps.setInt(2, entry.getRacerId());
            ps.setInt(3, entry.getFinishingPosition());
            ps.setInt(4, entry.isPodiumCounted() ? 1 : 0);
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                entry.setEntryId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert result entry: " + e.getMessage(), e);
        }
    }
}