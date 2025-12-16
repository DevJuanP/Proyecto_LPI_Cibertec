package repository;

import connection.DbContext;
import model.BookCopyStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookCopyStatusRepository implements IBookCopyStatusRepository {
    private final DbContext dbContext;
    
    public BookCopyStatusRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public BookCopyStatus findById(String bookCopyStatusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookCopyStatusId) as BookCopyStatusId, BookCopyStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookCopyStatus " +
                     "WHERE BookCopyStatusId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyStatusId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBookCopyStatus(rs);
            }
            return null;
        }
    }

    @Override
    public BookCopyStatus findByName(String bookCopyStatusName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookCopyStatusId) as BookCopyStatusId, BookCopyStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookCopyStatus " +
                     "WHERE BookCopyStatusName = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyStatusName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBookCopyStatus(rs);
            }
            return null;
        }
    }

    @Override
    public List<BookCopyStatus> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(BookCopyStatusId) as BookCopyStatusId, BookCopyStatusName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM BookCopyStatus " +
                     "ORDER BY BookCopyStatusName ASC";
        
        List<BookCopyStatus> statuses = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                statuses.add(mapResultSetToBookCopyStatus(rs));
            }
        }
        return statuses;
    }

    private BookCopyStatus mapResultSetToBookCopyStatus(ResultSet rs) throws SQLException {
        BookCopyStatus status = new BookCopyStatus();
        status.setBookCopyStatusId(rs.getString("BookCopyStatusId"));
        status.setBookCopyStatusName(rs.getString("BookCopyStatusName"));
        status.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return status;
    }
}