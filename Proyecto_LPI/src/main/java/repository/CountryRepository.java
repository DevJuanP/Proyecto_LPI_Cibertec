package repository;

import connection.DbContext;
import model.Country;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository implements ICountryRepository {
    private final DbContext dbContext;
    
    public CountryRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Country findById(String countryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CountryId) as CountryId, CountryName, CountryCode, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Country " +
                     "WHERE CountryId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, countryId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCountry(rs);
            }
            return null;
        }
    }

    @Override
    public Country findByName(String countryName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CountryId) as CountryId, CountryName, CountryCode, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Country " +
                     "WHERE CountryName = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCountry(rs);
            }
            return null;
        }
    }

    @Override
    public List<Country> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(CountryId) as CountryId, CountryName, CountryCode, " +
                     "CreatedAt, UpdatedAt " +
                     "FROM Country " +
                     "ORDER BY CountryName ASC";
        
        List<Country> countries = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                countries.add(mapResultSetToCountry(rs));
            }
        }
        return countries;
    }

    private Country mapResultSetToCountry(ResultSet rs) throws SQLException {
        Country country = new Country();
        country.setCountryId(rs.getString("CountryId"));
        country.setCountryName(rs.getString("CountryName"));
        country.setCountryCode(rs.getString("CountryCode"));
        country.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        country.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        return country;
    }
}