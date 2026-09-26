package bikr.repository;

import bikr.config.DatabaseConfig;
import bikr.model.*;
import bikr.model.enums.CategoryLevel;

import java.sql.*;

/**
 * Data access for users and their role-specific rows.
 * Owns the users, racers, organizers, and administrators tables.
 *
 * Exceptions are wrapped in RuntimeException so callers (controllers)
 * don't have to declare or catch SQLException.
 */
public class UserRepository {

    // ---------------------------------------------------------------
    // INSERT
    // ---------------------------------------------------------------

    /** Insert a new racer (users + racers rows). Returns the new user_id. */
    public int insertRacer(Racer racer) {
        String insertUser  = "INSERT INTO users (role, first_name, last_name, email, ssn, password) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String insertRacer = "INSERT INTO racers (user_id, current_podiums, category) "
                + "VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setString(1, "RACER");
                ps.setString(2, racer.getFirstName());
                ps.setString(3, racer.getLastName());
                ps.setString(4, racer.getEmail());
                ps.setString(5, racer.getSsn());
                ps.setString(6, racer.getPassword());
                ps.executeUpdate();
            }

            int newId = lastInsertedId(conn);
            racer.setUserId(newId);

            try (PreparedStatement ps = conn.prepareStatement(insertRacer)) {
                ps.setInt(1, newId);
                ps.setInt(2, racer.getCurrentPodiums());
                ps.setString(3, racer.getCategory().name());
                ps.executeUpdate();
            }

            conn.commit();
            return newId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert racer: " + e.getMessage(), e);
        }
    }

    /** Insert a new organizer (users + organizers rows). Returns the new user_id. */
    public int insertOrganizer(Organizer organizer) {
        String insertUser = "INSERT INTO users (role, first_name, last_name, email, ssn, password) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String insertOrg  = "INSERT INTO organizers (user_id) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setString(1, "ORGANIZER");
                ps.setString(2, organizer.getFirstName());
                ps.setString(3, organizer.getLastName());
                ps.setString(4, organizer.getEmail());
                ps.setString(5, organizer.getSsn());
                ps.setString(6, organizer.getPassword());
                ps.executeUpdate();
            }

            int newId = lastInsertedId(conn);
            organizer.setUserId(newId);

            try (PreparedStatement ps = conn.prepareStatement(insertOrg)) {
                ps.setInt(1, newId);
                ps.executeUpdate();
            }

            conn.commit();
            return newId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert organizer: " + e.getMessage(), e);
        }
    }

    /** Optional — only if you ever need to create admins at runtime. */
    public int insertAdministrator(Administrator admin) {
        String insertUser = "INSERT INTO users (role, first_name, last_name, email, ssn, password) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String insertAdm  = "INSERT INTO administrators (user_id) VALUES (?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setString(1, "ADMIN");
                ps.setString(2, admin.getFirstName());
                ps.setString(3, admin.getLastName());
                ps.setString(4, admin.getEmail());
                ps.setString(5, admin.getSsn());
                ps.setString(6, admin.getPassword());
                ps.executeUpdate();
            }

            int newId = lastInsertedId(conn);
            admin.setUserId(newId);

            try (PreparedStatement ps = conn.prepareStatement(insertAdm)) {
                ps.setInt(1, newId);
                ps.executeUpdate();
            }

            conn.commit();
            return newId;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert administrator: " + e.getMessage(), e);
        }
    }

    // ---------------------------------------------------------------
    // FIND
    // ---------------------------------------------------------------

    /** Used for sign-in. Returns a Racer, Organizer, or Administrator, or null. */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return buildUserFromRow(conn, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email: " + e.getMessage(), e);
        }
    }

    /** Used for admin sign-in. */
    public User findBySsn(String ssn) {
        String sql = "SELECT * FROM users WHERE ssn = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ssn);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return buildUserFromRow(conn, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by SSN: " + e.getMessage(), e);
        }
    }

    /** Load a racer by user_id (used by the observer and post-results flow). */
    public Racer findRacerById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = 'RACER'";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return (Racer) buildUserFromRow(conn, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find racer: " + e.getMessage(), e);
        }
    }

    // ---------------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------------

    /** Called by the observer after a podium upgrade. */
    public void updateRacerCategory(int userId, CategoryLevel newCategory, int newPodiums) {
        String updateRacer = "UPDATE racers SET category = ?, current_podiums = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(updateRacer)) {

            ps.setString(1, newCategory.name());
            ps.setInt(2, newPodiums);
            ps.setInt(3, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update racer category: " + e.getMessage(), e);
        }
    }

    // ---------------------------------------------------------------
    // HELPERS
    // ---------------------------------------------------------------

    /** SQLite-specific: returns the id generated by the last INSERT on this connection. */
    private int lastInsertedId(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
            rs.next();
            return rs.getInt(1);
        }
    }

    /** Builds a User subclass from a users-table row, loading role-specific fields if needed. */
    private User buildUserFromRow(Connection conn, ResultSet rs) throws SQLException {
        String role  = rs.getString("role");
        int    id    = rs.getInt("user_id");
        String fn    = rs.getString("first_name");
        String ln    = rs.getString("last_name");
        String email = rs.getString("email");
        String ssn   = rs.getString("ssn");
        String pw    = rs.getString("password");

        switch (role) {
            case "RACER": {
                Racer r = new Racer(fn, ln, email, ssn, pw);
                r.setUserId(id);
                loadRacerFields(conn, r);
                return r;
            }
            case "ORGANIZER": {
                Organizer o = new Organizer(fn, ln, email, ssn, pw);
                o.setUserId(id);
                return o;
            }
            case "ADMIN": {
                Administrator a = new Administrator(fn, ln, email, ssn, pw);
                a.setUserId(id);
                return a;
            }
            default:
                return null;
        }
    }

    /** Fills currentPodiums + category for a Racer. */
    private void loadRacerFields(Connection conn, Racer racer) throws SQLException {
        String sql = "SELECT current_podiums, category FROM racers WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, racer.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    racer.setCurrentPodiums(rs.getInt("current_podiums"));
                    racer.setCategory(CategoryLevel.valueOf(rs.getString("category")));
                }
            }
        }
    }
}