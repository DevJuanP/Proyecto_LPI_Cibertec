package repository;

import connection.DbContext;
import model.BookStatus;
import java.sql.*;
import java.util.ArrayList;

public class BookStatusRepository implements IBookStatusRepository {
    private final DbContext dbContext;
    
    public BookStatusRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public BookStatus findById(String bookStatusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookStatusId) as BookStatusId, BookStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookStatus " +
                     "WHERE BookStatusId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookStatusId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBookStatus(rs);
            }
            return null;
        }
    }

    @Override
    public BookStatus findByName(String bookStatusName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookStatusId) as BookStatusId, BookStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookStatus " +
                     "WHERE BookStatusName = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookStatusName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBookStatus(rs);
            }
            return null;
        }
    }

    @Override
    public ArrayList<BookStatus> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookStatusId) as BookStatusId, BookStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookStatus " +
                     "ORDER BY BookStatusName ASC";
        
        ArrayList<BookStatus> statuses = new ArrayList<>(2);
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                statuses.add(mapResultSetToBookStatus(rs));
            }
        }
        return statuses;
    }

    private BookStatus mapResultSetToBookStatus(ResultSet rs) throws SQLException {
        BookStatus status = new BookStatus();
        status.setBookStatusId(rs.getString("BookStatusId"));
        status.setBookStatusName(rs.getString("BookStatusName"));
        status.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return status;
    }
}