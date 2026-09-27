package bikr.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {

    private static final String DB_FILE = "bikr.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        if (!initialized) {
            initSchema(conn);
            seedIfEmpty(conn);
            initialized = true;
        }
        return conn;
    }

    private static void initSchema(Connection conn) throws SQLException {
        runSqlFile(conn, "sql/schema.sql");
    }

    private static void seedIfEmpty(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             var rs = st.executeQuery("SELECT COUNT(*) FROM users")) {
            if (rs.next() && rs.getInt(1) == 0) {
                runSqlFile(conn, "sql/seed.sql");
            }
        }
    }

    private static void runSqlFile(Connection conn, String path) throws SQLException {
        try {
            String sql = Files.readString(Path.of(path));
            try (Statement st = conn.createStatement()) {
                for (String stmt : sql.split(";")) {
                    String trimmed = stmt.trim();
                    if (!trimmed.isEmpty()) {
                        st.execute(trimmed);
                    }
                }
            }
        } catch (IOException e) {
            throw new SQLException("Could not read " + path, e);
        }
    }
}