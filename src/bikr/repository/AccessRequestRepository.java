package bikr.repository;
import bikr.config.DatabaseConfig;
import bikr.model.AccessRequest;
import bikr.model.enums.RequestStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessRequestRepository {

    public int insert(AccessRequest request) {
        String sql = "INSERT INTO access_requests (user_id, status) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, request.getUserId());
            ps.setString(2, request.getStatus().name());
            ps.executeUpdate();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT last_insert_rowid()")) {
                rs.next();
                int id = rs.getInt(1);
                request.setRequestId(id);
                return id;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert access request: " + e.getMessage(), e);
        }
    }

    public List<AccessRequest> findPending() {
        String sql = "SELECT * FROM access_requests WHERE status = 'PENDING'";
        List<AccessRequest> list = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                AccessRequest ar = new AccessRequest();
                ar.setRequestId(rs.getInt("request_id"));
                ar.setUserId(rs.getInt("user_id"));
                ar.setStatus(RequestStatus.valueOf(rs.getString("status")));
                list.add(ar);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load pending requests: " + e.getMessage(), e);
        }
    }

    public void updateStatus(int requestId, RequestStatus status) {
        String sql = "UPDATE access_requests SET status = ? WHERE request_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, requestId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update request status: " + e.getMessage(), e);
        }
    }
}