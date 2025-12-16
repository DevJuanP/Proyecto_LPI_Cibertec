package repository;

import connection.DatabaseConnection;
import model.Author;
import model.Country;
import model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthorRepository implements IAuthorRepository {

    @Override
    public Author findById(String authorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                     "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                     "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email, a.PhotoUrl, " +
                     "a.CreatedAt, a.UpdatedAt, " +
                     "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                     "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                     "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM Author a " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "WHERE a.AuthorId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, authorId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAuthor(rs);
            }
            return null;
        }
    }

    @Override
    public Author findByName(String fullName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                     "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                     "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email, a.PhotoUrl, " +
                     "a.CreatedAt, a.UpdatedAt, " +
                     "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                     "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                     "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM Author a " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "WHERE a.FullName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToAuthor(rs);
            }
            return null;
        }
    }

    @Override
    public List<Author> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                     "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                     "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email, a.PhotoUrl, " +
                     "a.CreatedAt, a.UpdatedAt, " +
                     "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                     "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                     "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM Author a " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "ORDER BY a.FullName ASC";
        
        List<Author> authors = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    @Override
    public List<Author> findByCountry(String countryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                     "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                     "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email, a.PhotoUrl, " +
                     "a.CreatedAt, a.UpdatedAt, " +
                     "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                     "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                     "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM Author a " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "WHERE a.CountryId = UUID_TO_BIN(?) " +
                     "ORDER BY a.FullName ASC";
        
        List<Author> authors = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, countryId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    @Override
    public List<Author> findByStatus(String statusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                     "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                     "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email, a.PhotoUrl, " +
                     "a.CreatedAt, a.UpdatedAt, " +
                     "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                     "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                     "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                     "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                     "FROM Author a " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "WHERE a.StatusId = UUID_TO_BIN(?) " +
                     "ORDER BY a.FullName ASC";
        
        List<Author> authors = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, statusId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    @Override
    public void save(Author author) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Author (AuthorId, FullName, Pseudonym, CountryId, StatusId, " +
                     "Biography, BirthDate, DeathDate, Website, Email, PhotoUrl) " +
                     "VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String authorId = UUID.randomUUID().toString();
            author.setAuthorId(authorId);
            
            ps.setString(1, authorId);
            ps.setString(2, author.getFullName());
            ps.setString(3, author.getPseudonym());
            ps.setString(4, author.getCountryId());
            ps.setString(5, author.getStatusId());
            ps.setString(6, author.getBiography());
            
            if (author.getBirthDate() != null) {
                ps.setDate(7, Date.valueOf(author.getBirthDate()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            
            if (author.getDeathDate() != null) {
                ps.setDate(8, Date.valueOf(author.getDeathDate()));
            } else {
                ps.setNull(8, Types.DATE);
            }
            
            ps.setString(9, author.getWebsite());
            ps.setString(10, author.getEmail());
            ps.setString(11, author.getPhotoUrl());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Author author) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Author SET FullName = ?, Pseudonym = ?, CountryId = UUID_TO_BIN(?), " +
                     "StatusId = UUID_TO_BIN(?), Biography = ?, BirthDate = ?, DeathDate = ?, " +
                     "Website = ?, Email = ?, PhotoUrl = ? " +
                     "WHERE AuthorId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, author.getFullName());
            ps.setString(2, author.getPseudonym());
            ps.setString(3, author.getCountryId());
            ps.setString(4, author.getStatusId());
            ps.setString(5, author.getBiography());
            
            if (author.getBirthDate() != null) {
                ps.setDate(6, Date.valueOf(author.getBirthDate()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            
            if (author.getDeathDate() != null) {
                ps.setDate(7, Date.valueOf(author.getDeathDate()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            
            ps.setString(8, author.getWebsite());
            ps.setString(9, author.getEmail());
            ps.setString(10, author.getPhotoUrl());
            ps.setString(11, author.getAuthorId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String authorId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Author WHERE AuthorId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, authorId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByName(String fullName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Author WHERE FullName = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setAuthorId(rs.getString("AuthorId"));
        author.setFullName(rs.getString("FullName"));
        author.setPseudonym(rs.getString("Pseudonym"));
        author.setCountryId(rs.getString("CountryId"));
        author.setStatusId(rs.getString("StatusId"));
        author.setBiography(rs.getString("Biography"));
        
        Date birthDate = rs.getDate("BirthDate");
        if (birthDate != null) {
            author.setBirthDate(birthDate.toLocalDate());
        }
        
        Date deathDate = rs.getDate("DeathDate");
        if (deathDate != null) {
            author.setDeathDate(deathDate.toLocalDate());
        }
        
        author.setWebsite(rs.getString("Website"));
        author.setEmail(rs.getString("Email"));
        author.setPhotoUrl(rs.getString("PhotoUrl"));
        author.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        author.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        
        Country country = new Country();
        country.setCountryId(rs.getString("CountryIdFull"));
        country.setCountryName(rs.getString("CountryName"));
        country.setCountryCode(rs.getString("CountryCode"));
        country.setCreatedAt(rs.getTimestamp("CountryCreatedAt").toLocalDateTime());
        country.setUpdatedAt(rs.getTimestamp("CountryUpdatedAt").toLocalDateTime());
        author.setCountry(country);
        
        Status status = new Status();
        status.setStatusId(rs.getString("StatusIdFull"));
        status.setStatusName(rs.getString("StatusName"));
        status.setCreatedAt(rs.getTimestamp("StatusCreatedAt").toLocalDateTime());
        status.setUpdatedAt(rs.getTimestamp("StatusUpdatedAt").toLocalDateTime());
        author.setStatus(status);
        
        return author;
    }
}