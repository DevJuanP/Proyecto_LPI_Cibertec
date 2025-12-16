package repository;

import connection.DatabaseConnection;
import model.RentalStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalStatusRepository implements IRentalStatusRepository {

    @Override
    public RentalStatus findById(String rentalStatusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RentalStatusId) as RentalStatusId, RentalStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM RentalStatus " +
                     "WHERE RentalStatusId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rentalStatusId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRentalStatus(rs);
            }
            return null;
        }
    }

    @Override
    public RentalStatus findByName(String rentalStatusName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RentalStatusId) as RentalStatusId, RentalStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM RentalStatus " +
                     "WHERE RentalStatusName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rentalStatusName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRentalStatus(rs);
            }
            return null;
        }
    }

    @Override
    public List<RentalStatus> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RentalStatusId) as RentalStatusId, RentalStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM RentalStatus " +
                     "ORDER BY RentalStatusName ASC";
        
        List<RentalStatus> statuses = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                statuses.add(mapResultSetToRentalStatus(rs));
            }
        }
        return statuses;
    }

    private RentalStatus mapResultSetToRentalStatus(ResultSet rs) throws SQLException {
        RentalStatus status = new RentalStatus();
        status.setRentalStatusId(rs.getString("RentalStatusId"));
        status.setRentalStatusName(rs.getString("RentalStatusName"));
        status.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return status;
    }
}