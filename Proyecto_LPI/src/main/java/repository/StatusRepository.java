package repository;

import connection.DatabaseConnection;
import model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusRepository implements IStatusRepository {

    @Override
    public Status findById(String statusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(StatusId) as StatusId, StatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Status " +
                     "WHERE StatusId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, statusId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStatus(rs);
            }
            return null;
        }
    }

    @Override
    public Status findByName(String statusName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(StatusId) as StatusId, StatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Status " +
                     "WHERE StatusName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, statusName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStatus(rs);
            }
            return null;
        }
    }

    @Override
    public List<Status> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(StatusId) as StatusId, StatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Status " +
                     "ORDER BY StatusName ASC";
        
        List<Status> statuses = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                statuses.add(mapResultSetToStatus(rs));
            }
        }
        return statuses;
    }

    private Status mapResultSetToStatus(ResultSet rs) throws SQLException {
        Status status = new Status();
        status.setStatusId(rs.getString("StatusId"));
        status.setStatusName(rs.getString("StatusName"));
        status.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return status;
    }
}