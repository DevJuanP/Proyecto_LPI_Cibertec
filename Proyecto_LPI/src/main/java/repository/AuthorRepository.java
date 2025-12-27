package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import connection.DbContext;
import model.Author;
import model.AuthorStatsData;
import model.Country;
import model.Status;

public class AuthorRepository implements IAuthorRepository {
    private final DbContext dbContext;

    public AuthorRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Author findById(String authorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
                "a.CreatedAt, a.UpdatedAt, " +
                "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                "FROM Author a " +
                "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                "WHERE a.AuthorId = UUID_TO_BIN(?)";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

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
                "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
                "a.CreatedAt, a.UpdatedAt, " +
                "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                "FROM Author a " +
                "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                "WHERE a.FullName = ?";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToAuthor(rs);
            }
            return null;
        }
    }

    @Override
    public LinkedList<Author> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
                "a.CreatedAt, a.UpdatedAt, " +
                "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                "FROM Author a " +
                "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                "ORDER BY a.FullName ASC";

        LinkedList<Author> authors = new LinkedList<>();

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    @Override
    public LinkedList<Author> findByCountry(String countryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
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

        LinkedList<Author> authors = new LinkedList<>();
        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, countryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                authors.add(mapResultSetToAuthor(rs));
            }
        }
        return authors;
    }

    @Override
    public LinkedList<Author> findByStatus(String statusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
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

        LinkedList<Author> authors = new LinkedList<>();
        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

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
                "Biography, BirthYear, DeathYear, Website, Email, PhotoUrl) " +
                "VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?)";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            String authorId = UUID.randomUUID().toString();
            author.setAuthorId(authorId);

            ps.setString(1, authorId);
            ps.setString(2, author.getFullName());
            ps.setString(3, author.getPseudonym());
            ps.setString(4, author.getCountryId());
            ps.setString(5, author.getStatusId());
            ps.setString(6, author.getBiography());

            if (author.getBirthYear() != null) {
                ps.setInt(7, author.getBirthYear());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            if (author.getDeathYear() != null) {
                ps.setInt(8, author.getDeathYear());
            } else {
                ps.setNull(8, Types.INTEGER);
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
                "StatusId = UUID_TO_BIN(?), Biography = ?, BirthYear = ?, DeathYear = ?, " +
                "Website = ?, Email = ?, PhotoUrl = ? " +
                "WHERE AuthorId = UUID_TO_BIN(?)";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, author.getFullName());
            ps.setString(2, author.getPseudonym());
            ps.setString(3, author.getCountryId());
            ps.setString(4, author.getStatusId());
            ps.setString(5, author.getBiography());

            if (author.getBirthYear() != null) {
                ps.setInt(6, author.getBirthYear());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            if (author.getDeathYear() != null) {
                ps.setInt(7, author.getDeathYear());
            } else {
                ps.setNull(7, Types.INTEGER);
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

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, authorId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByName(String fullName) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Author WHERE FullName = ?";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    @Override
    public int countAuthorBooks(String authorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                       "COUNT(b.BookId) " + 
                    "FROM " +
                        "Author a " +
                        "INNER JOIN Book b ON a.AuthorId = b.AuthorId " +
                    "WHERE " +
                        "a.AuthorId = UUID_TO_BIN(?)";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, authorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int count(String search, String countryId, String statusId) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Author a WHERE 1=1");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (a.FullName LIKE ? OR a.Pseudonym LIKE ?)");
        }
        if (countryId != null && !countryId.trim().isEmpty()) {
            sql.append(" AND a.CountryId = UUID_TO_BIN(?)");
        }
        if (statusId != null && !statusId.trim().isEmpty()) {
            sql.append(" AND a.StatusId = UUID_TO_BIN(?)");
        }

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (countryId != null && !countryId.trim().isEmpty()) {
                ps.setString(paramIndex++, countryId);
            }
            if (statusId != null && !statusId.trim().isEmpty()) {
                ps.setString(paramIndex++, statusId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public LinkedList<Author> findAllPaginated(int offset, int limit) throws SQLException, ClassNotFoundException {
        return findAllPaginated(offset, limit, null, null, null);
    }

    @Override
    public LinkedList<Author> findAllPaginated(int offset, int limit, String search, String countryId, String statusId)
            throws SQLException, ClassNotFoundException {

        StringBuilder sql = new StringBuilder(
                "SELECT BIN_TO_UUID(a.AuthorId) as AuthorId, a.FullName, a.Pseudonym, " +
                        "BIN_TO_UUID(a.CountryId) as CountryId, BIN_TO_UUID(a.StatusId) as StatusId, " +
                        "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
                        "a.CreatedAt, a.UpdatedAt, " +
                        "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
                        "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
                        "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
                        "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt " +
                        "FROM Author a " +
                        "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                        "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                        "WHERE 1=1");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (a.FullName LIKE ? OR a.Pseudonym LIKE ?)");
        }
        if (countryId != null && !countryId.trim().isEmpty()) {
            sql.append(" AND a.CountryId = UUID_TO_BIN(?)");
        }
        if (statusId != null && !statusId.trim().isEmpty()) {
            sql.append(" AND a.StatusId = UUID_TO_BIN(?)");
        }

        sql.append(" ORDER BY a.FullName ASC LIMIT ? OFFSET ?");

        LinkedList<Author> authors = new LinkedList<>();
        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (countryId != null && !countryId.trim().isEmpty()) {
                ps.setString(paramIndex++, countryId);
            }
            if (statusId != null && !statusId.trim().isEmpty()) {
                ps.setString(paramIndex++, statusId);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    authors.add(mapResultSetToAuthor(rs));
                }
            }
        }
        return authors;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Author";

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public List<AuthorStatsData> getMostRequestedAuthors(
            String countryId, String statusId, int limit) 
            throws SQLException, ClassNotFoundException {
        
        StringBuilder sql = new StringBuilder(
            """
                SELECT 
                    BIN_TO_UUID(a.AuthorId) as AuthorId, 
                    a.FullName, 
                    a.Pseudonym, 
                    a.PhotoUrl, 
                    c.CountryName, 
                    COUNT(DISTINCT b.BookId) as TotalBooks, 
                    COUNT(DISTINCT bc.BookCopyId) as TotalCopies, 
                    SUM(CASE WHEN bcs.BookCopyStatusName = 'Disponible' THEN 1 ELSE 0 END) as AvailableCopies, 
                    COUNT(r.RentalId) as TotalRentals 
                FROM Author a 
                INNER JOIN Country c ON a.CountryId = c.CountryId 
                INNER JOIN Status s ON a.StatusId = s.StatusId 
                LEFT JOIN Book b ON a.AuthorId = b.AuthorId 
                LEFT JOIN BookCopy bc ON b.BookId = bc.BookId 
                LEFT JOIN BookCopyStatus bcs ON bc.BookCopyStatusId = bcs.BookCopyStatusId 
                LEFT JOIN Rental r ON bc.BookCopyId = r.BookCopyId 
                WHERE 1=1    
            """
        );
        
        if (countryId != null && !countryId.trim().isEmpty()) {
            sql.append(" AND a.CountryId = UUID_TO_BIN(?) ");
        }
        if (statusId != null && !statusId.trim().isEmpty()) {
            sql.append(" AND a.StatusId = UUID_TO_BIN(?) ");
        }
        
        sql.append(
            "GROUP BY a.AuthorId, a.FullName, a.Pseudonym, c.CountryName " +
            "HAVING TotalRentals > 0 " +
            "ORDER BY TotalRentals DESC, a.FullName ASC " +
            "LIMIT ?"
        );
        
        ArrayList<AuthorStatsData> statsList = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            
            if (countryId != null && !countryId.trim().isEmpty()) {
                ps.setString(paramIndex++, countryId);
            }
            if (statusId != null && !statusId.trim().isEmpty()) {
                ps.setString(paramIndex++, statusId);
            }
            
            ps.setInt(paramIndex, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AuthorStatsData stats = new AuthorStatsData(
                        rs.getString("AuthorId"),
                        rs.getString("FullName"),
                        rs.getString("Pseudonym"),
                        rs.getString("PhotoUrl"),
                        rs.getString("CountryName"),
                        rs.getInt("TotalBooks"),
                        rs.getInt("TotalCopies"),
                        rs.getInt("AvailableCopies"),
                        rs.getInt("TotalRentals")
                    );
                    statsList.add(stats);
                }
            }
        }
        
        return statsList;
    }

    @Override
    public int countAuthorsWithRentals() throws SQLException, ClassNotFoundException {
        String sql = 
            "SELECT COUNT(DISTINCT a.AuthorId) " +
            "FROM Author a " +
            "INNER JOIN Book b ON a.AuthorId = b.AuthorId " +
            "INNER JOIN BookCopy bc ON b.BookId = bc.BookId " +
            "INNER JOIN Rental r ON bc.BookCopyId = r.BookCopyId";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int getTotalAuthorsRentals() throws SQLException, ClassNotFoundException {
        String sql = 
            "SELECT COUNT(r.RentalId) " +
            "FROM Rental r " +
            "INNER JOIN BookCopy bc ON r.BookCopyId = bc.BookCopyId " +
            "INNER JOIN Book b ON bc.BookId = b.BookId " +
            "INNER JOIN Author a ON b.AuthorId = a.AuthorId";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int countAuthorsWithBooks() throws SQLException, ClassNotFoundException {
        String sql = 
            "SELECT COUNT(DISTINCT a.AuthorId) " +
            "FROM Author a " +
            "INNER JOIN Book b ON a.AuthorId = b.AuthorId";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
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
        author.setBirthYear(rs.getObject("BirthYear", Integer.class));
        author.setDeathYear(rs.getObject("DeathYear", Integer.class));
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