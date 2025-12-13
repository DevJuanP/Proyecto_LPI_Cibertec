package repository;

import connection.DatabaseConnection;
import model.User;
import model.Role;
import model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    public User findById(String userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(u.UserId) as UserId, u.Email, u.Password, " +
                     "BIN_TO_UUID(u.StatusId) as StatusId, u.CreatedAt, u.UpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM User u " +
                     "INNER JOIN Status s ON u.StatusId = s.StatusId " +
                     "WHERE u.UserId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                user.setRoles(findRolesByUserId(userId));
                return user;
            }
            return null;
        }
    }

    public User findByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(u.UserId) as UserId, u.Email, u.Password, " +
                     "BIN_TO_UUID(u.StatusId) as StatusId, u.CreatedAt, u.UpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM User u " +
                     "INNER JOIN Status s ON u.StatusId = s.StatusId " +
                     "WHERE u.Email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                user.setRoles(findRolesByUserId(user.getUserId()));
                return user;
            }
            return null;
        }
    }

    public List<User> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(u.UserId) as UserId, u.Email, u.Password, " +
                     "BIN_TO_UUID(u.StatusId) as StatusId, u.CreatedAt, u.UpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM User u " +
                     "INNER JOIN Status s ON u.StatusId = s.StatusId " +
                     "ORDER BY u.CreatedAt DESC";
        
        List<User> users = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                user.setRoles(findRolesByUserId(user.getUserId()));
                users.add(user);
            }
        }
        return users;
    }

    public void save(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO User (UserId, Email, Password, StatusId) " +
                     "VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String userId = UUID.randomUUID().toString();
            user.setUserId(userId);
            
            ps.setString(1, userId);
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getStatusId());
            
            ps.executeUpdate();
        }
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE User SET Email = ?, Password = ?, StatusId = UUID_TO_BIN(?) " +
                     "WHERE UserId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getStatusId());
            ps.setString(4, user.getUserId());
            
            ps.executeUpdate();
        }
    }

    public void delete(String userId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM User WHERE UserId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ps.executeUpdate();
        }
    }

    public boolean existsByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM User WHERE Email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public List<Role> findRolesByUserId(String userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(r.RoleId) as RoleId, r.RoleName, r.CreatedAt, r.UpdatedAt " +
                     "FROM Role r " +
                     "INNER JOIN UserRole ur ON r.RoleId = ur.RoleId " +
                     "WHERE ur.UserId = UUID_TO_BIN(?)";
        
        List<Role> roles = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Role role = new Role();
                role.setRoleId(rs.getString("RoleId"));
                role.setRoleName(rs.getString("RoleName"));
                role.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
                role.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
                roles.add(role);
            }
        }
        return roles;
    }

    public void addRoleToUser(String userId, String roleId) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO UserRole (UserRoleId, UserId, RoleId) " +
                     "VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?))";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, userId);
            ps.setString(3, roleId);
            
            ps.executeUpdate();
        }
    }

    public void removeRoleFromUser(String userId, String roleId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM UserRole WHERE UserId = UUID_TO_BIN(?) AND RoleId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ps.setString(2, roleId);
            
            ps.executeUpdate();
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getString("UserId"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setStatusId(rs.getString("StatusId"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        
        Status status = new Status();
        status.setStatusId(rs.getString("StatusIdFull"));
        status.setStatusName(rs.getString("StatusName"));
        status.setCreatedAt(rs.getTimestamp("StatusCreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("StatusUpdatedAt").toLocalDateTime());
        user.setStatus(status);
        
        return user;
    }
}