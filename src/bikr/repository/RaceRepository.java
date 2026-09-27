package bikr.repository;

import bikr.config.DatabaseConfig;
import bikr.model.Race;
import bikr.model.enums.RaceType;
import bikr.pattern.RaceBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaceRepository {

    public int insert(Race race) {
        String sql = "INSERT INTO races (organizer_id, race_name, race_officiality, race_date, "
                + "race_type, race_miles, race_route, race_location, race_max_registrations, "
                + "race_last_day_registrations) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, race.getOrganizerId());
            ps.setString(2, race.getRaceName());
            ps.setInt(3, race.isRaceOfficiality() ? 1 : 0);
            ps.setString(4, race.getRaceDate().toString());
            ps.setString(5, race.getRaceType().name());
            ps.setDouble(6, race.getRaceMiles());
            ps.setString(7, race.getRaceRoute());
            ps.setString(8, race.getRaceLocation());
            ps.setInt(9, race.getRaceMaxRegistrations());
            ps.setString(10, race.getRaceLastDayRegistrations().toString());
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                race.setRaceId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert race: " + e.getMessage(), e);
        }
    }

    public List<Race> findAll() {
        String sql = "SELECT * FROM races ORDER BY race_id";
        List<Race> list = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(buildRace(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load races: " + e.getMessage(), e);
        }
    }

    public Race findById(int raceId) {
        String sql = "SELECT * FROM races WHERE race_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, raceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return buildRace(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find race: " + e.getMessage(), e);
        }
    }

    private Race buildRace(ResultSet rs) throws SQLException {
        RaceBuilder b = new RaceBuilder(
                rs.getString("race_name"),
                LocalDate.parse(rs.getString("race_date"))
        );
        b.setType(RaceType.valueOf(rs.getString("race_type")));
        b.setOfficiality(rs.getInt("race_officiality") == 1);
        b.setMiles(rs.getDouble("race_miles"));
        b.setRoute(rs.getString("race_route"));
        b.setLocation(rs.getString("race_location"));
        b.setMaxRegistrations(rs.getInt("race_max_registrations"));
        b.setLastDayRegistrations(LocalDate.parse(rs.getString("race_last_day_registrations")));

        Race r = b.build();
        r.setRaceId(rs.getInt("race_id"));
        r.setOrganizerId(rs.getInt("organizer_id"));
        return r;
    }
}