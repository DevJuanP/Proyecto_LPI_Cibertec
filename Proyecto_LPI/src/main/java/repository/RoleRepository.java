package repository;

import connection.DbContext;
import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository implements IRoleRepository {
    private final DbContext dbContext;
    
    public RoleRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Role findById(String roleId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RoleId) as RoleId, RoleName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Role " +
                     "WHERE RoleId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRole(rs);
            }
            return null;
        }
    }

    @Override
    public Role findByName(String roleName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RoleId) as RoleId, RoleName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Role " +
                     "WHERE RoleName = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRole(rs);
            }
            return null;
        }
    }

    @Override
    public List<Role> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(RoleId) as RoleId, RoleName, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Role " +
                     "ORDER BY RoleName ASC";
        
        List<Role> roles = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        }
        return roles;
    }

    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getString("RoleId"));
        role.setRoleName(rs.getString("RoleName"));
        role.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        role.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return role;
    }
}