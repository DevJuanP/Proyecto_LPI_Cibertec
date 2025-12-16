package repository;

import connection.DatabaseConnection;
import model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryRepository implements ICategoryRepository {

    @Override
    public Category findById(String categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CategoryId) as CategoryId, CategoryName, Description, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Category " +
                     "WHERE CategoryId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCategory(rs);
            }
            return null;
        }
    }

    @Override
    public Category findByName(String categoryName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CategoryId) as CategoryId, CategoryName, Description, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Category " +
                     "WHERE CategoryName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCategory(rs);
            }
            return null;
        }
    }

    @Override
    public List<Category> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CategoryId) as CategoryId, CategoryName, Description, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Category " +
                     "ORDER BY CategoryName ASC";
        
        List<Category> categories = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        }
        return categories;
    }

    @Override
    public void save(Category category) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Category (CategoryId, CategoryName, Description) " +
                     "VALUES (UUID_TO_BIN(?), ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String categoryId = UUID.randomUUID().toString();
            category.setCategoryId(categoryId);
            
            ps.setString(1, categoryId);
            ps.setString(2, category.getCategoryName());
            ps.setString(3, category.getDescription());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Category category) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Category SET CategoryName = ?, Description = ? " +
                     "WHERE CategoryId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getCategoryId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String categoryId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Category WHERE CategoryId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoryId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByName(String categoryName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Category WHERE CategoryName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategoryId(rs.getString("CategoryId"));
        category.setCategoryName(rs.getString("CategoryName"));
        category.setDescription(rs.getString("Description"));
        category.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        category.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return category;
    }
}