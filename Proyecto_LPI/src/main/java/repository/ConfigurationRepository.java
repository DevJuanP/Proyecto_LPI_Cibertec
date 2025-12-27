package repository;

import connection.DbContext;
import model.Configuration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationRepository implements IConfigurationRepository {
    private final DbContext dbContext;
    
    public ConfigurationRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Configuration findById(String configurationId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(ConfigurationId) as ConfigurationId, ConfigKey, ConfigValue, " +
                     "ConfigType, DisplayName, Description, CreatedAt, UpdatedAt " +
                     "FROM Configuration " +
                     "WHERE ConfigurationId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, configurationId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToConfiguration(rs);
            }
            return null;
        }
    }

    @Override
    public Configuration findByKey(String configKey) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(ConfigurationId) as ConfigurationId, ConfigKey, ConfigValue, " +
                     "ConfigType, DisplayName, Description, CreatedAt, UpdatedAt " +
                     "FROM Configuration " +
                     "WHERE ConfigKey = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, configKey);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToConfiguration(rs);
            }
            return null;
        }
    }

    @Override
    public List<Configuration> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(ConfigurationId) as ConfigurationId, ConfigKey, ConfigValue, " +
                     "ConfigType, DisplayName, Description, CreatedAt, UpdatedAt " +
                     "FROM Configuration " +
                     "ORDER BY DisplayName ASC";
        
        List<Configuration> configurations = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                configurations.add(mapResultSetToConfiguration(rs));
            }
        }
        return configurations;
    }

    @Override
    public void update(Configuration configuration) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Configuration " +
                     "SET ConfigValue = ?, ConfigType = ?, DisplayName = ?, Description = ? " +
                     "WHERE ConfigurationId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, configuration.getConfigValue());
            ps.setString(2, configuration.getConfigType());
            ps.setString(3, configuration.getDisplayName());
            ps.setString(4, configuration.getDescription());
            ps.setString(5, configuration.getConfigurationId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByKey(String configKey) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) as count FROM Configuration WHERE ConfigKey = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, configKey);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        }
    }

    private Configuration mapResultSetToConfiguration(ResultSet rs) throws SQLException {
        Configuration config = new Configuration();
        config.setConfigurationId(rs.getString("ConfigurationId"));
        config.setConfigKey(rs.getString("ConfigKey"));
        config.setConfigValue(rs.getString("ConfigValue"));
        config.setConfigType(rs.getString("ConfigType"));
        config.setDisplayName(rs.getString("DisplayName"));
        config.setDescription(rs.getString("Description"));
        config.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        config.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return config;
    }
}