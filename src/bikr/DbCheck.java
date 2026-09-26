package bikr;

import bikr.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbCheck {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement st = conn.createStatement()) {

            System.out.println("Connected to SQLite successfully!");

            // Check tables
            ResultSet tables = st.executeQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name");
            System.out.println("\nTables:");
            while (tables.next()) {
                System.out.println("  - " + tables.getString("name"));
            }

            // Check seeded data
            ResultSet users = st.executeQuery("SELECT user_id, role, email FROM users");
            System.out.println("\nUsers:");
            while (users.next()) {
                System.out.println("  - " + users.getInt("user_id")
                        + " | " + users.getString("role")
                        + " | " + users.getString("email"));
            }

            ResultSet races = st.executeQuery("SELECT race_id, race_name FROM races");
            System.out.println("\nRaces:");
            while (races.next()) {
                System.out.println("  - " + races.getInt("race_id")
                        + " | " + races.getString("race_name"));
            }

        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}