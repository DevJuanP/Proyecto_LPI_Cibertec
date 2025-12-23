package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import connection.DbContext;
import model.Author;
import model.Book;
import model.BookCopy;
import model.BookCopyStatus;
import model.BookStatus;
import model.Category;
import model.Country;
import model.Status;

public class BookCopyRepository implements IBookCopyRepository {
    private final DbContext dbContext;
    
    public BookCopyRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public BookCopy findById(String bookCopyId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + " WHERE bc.BookCopyId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBookCopy(rs);
            }
            return null;
        }
    }

    @Override
    public List<BookCopy> findAll() throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + " ORDER BY b.Title ASC, bc.CreatedAt ASC";
        
        List<BookCopy> bookCopies = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findByBook(String bookId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE bc.BookId = UUID_TO_BIN(?) " +
                     " ORDER BY bc.CreatedAt ASC";
        
        List<BookCopy> bookCopies = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findByStatus(String bookCopyStatusId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE bc.BookCopyStatusId = UUID_TO_BIN(?) " +
                     " ORDER BY b.Title ASC, bc.CreatedAt ASC";
        
        List<BookCopy> bookCopies = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyStatusId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        }
        return bookCopies;
    }

    @Override
    public List<BookCopy> findAvailableByBook(String bookId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE bc.BookId = UUID_TO_BIN(?) " +
                     " AND bcs.BookCopyStatusName = 'Available' " +
                     " ORDER BY bc.CreatedAt ASC";
        
        List<BookCopy> bookCopies = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookCopies.add(mapResultSetToBookCopy(rs));
            }
        }
        return bookCopies;
    }

    @Override
    public void save(BookCopy bookCopy) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO BookCopy (BookCopyId, BookId, BookCopyStatusId, Notes) " +
                     "VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?), ?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String bookCopyId = UUID.randomUUID().toString();
            bookCopy.setBookCopyId(bookCopyId);
            
            ps.setString(1, bookCopyId);
            ps.setString(2, bookCopy.getBookId());
            ps.setString(3, bookCopy.getBookCopyStatusId());
            ps.setString(4, bookCopy.getNotes());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void update(BookCopy bookCopy) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE BookCopy SET BookId = UUID_TO_BIN(?), " +
                     "BookCopyStatusId = UUID_TO_BIN(?), Notes = ? " +
                     "WHERE BookCopyId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopy.getBookId());
            ps.setString(2, bookCopy.getBookCopyStatusId());
            ps.setString(3, bookCopy.getNotes());
            ps.setString(4, bookCopy.getBookCopyId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String bookCopyId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM BookCopy WHERE BookCopyId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyId);
            ps.executeUpdate();
        }
    }

    @Override
    public int countByBook(String bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM BookCopy WHERE BookId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int countAvailableByBook(String bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM BookCopy bc " +
                     "INNER JOIN BookCopyStatus bcs ON bc.BookCopyStatusId = bcs.BookCopyStatusId " +
                     "WHERE bc.BookId = UUID_TO_BIN(?) AND bcs.BookCopyStatusName = 'Available'";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM BookCopy";

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
    public int count(String search, String bookId, String bookCopyStatusId) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT " +
                                                "COUNT(bc.BookCopyId) " +
                                            "FROM " +
                                                "BookCopy bc " +
                                                "INNER JOIN Book b ON bc.BookId = b.BookId " +
                                            "WHERE " +
                                                "1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND b.Title LIKE ? ");
        }
        if (bookId != null && !bookId.trim().isEmpty()) {
            sql.append(" AND bc.BookId = UUID_TO_BIN(?)");
        }
        if (bookCopyStatusId != null && !bookCopyStatusId.trim().isEmpty()) {
            sql.append(" AND bc.BookCopyStatusId = UUID_TO_BIN(?)");
        }

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (bookId != null && !bookId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookId);
            }
            if (bookCopyStatusId != null && !bookCopyStatusId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookCopyStatusId);
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
    public LinkedList<BookCopy> findAllPaginated(int offset, int limit, String search, String bookId, String bookCopyStatusId) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder(buildSelectQuery() + "WHERE 1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (b.ISBN LIKE ? OR b.Title LIKE ?)");
        }
        if (bookId != null && !bookId.trim().isEmpty()) {
            sql.append(" AND bc.BookId = UUID_TO_BIN(?) ");
        }
        if (bookCopyStatusId != null && !bookCopyStatusId.trim().isEmpty()) {
            sql.append(" AND bc.BookCopyStatusId = UUID_TO_BIN(?) ");
        }

        sql.append(" ORDER BY b.Title ASC, bc.CreatedAt ASC LIMIT ? OFFSET ?");
        
        LinkedList<BookCopy> bookCopies = new LinkedList<>();
        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (bookId != null && !bookId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookId);
            }
            if (bookCopyStatusId != null && !bookCopyStatusId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookCopyStatusId);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bookCopies.add(mapResultSetToBookCopy(rs));
                }
            }
        }
        return bookCopies;
    }

    private String buildSelectQuery() {
        return "SELECT " +
               // BookCopy
               "BIN_TO_UUID(bc.BookCopyId) as BookCopyId, BIN_TO_UUID(bc.BookId) as BookId, " +
               "BIN_TO_UUID(bc.BookCopyStatusId) as BookCopyStatusId, bc.Notes, " +
               "bc.CreatedAt as BookCopyCreatedAt, bc.UpdatedAt as BookCopyUpdatedAt, " +
               // Book
               "BIN_TO_UUID(b.BookId) as BookIdFull, b.ISBN, b.Title, " +
               "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
               "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
               "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
               "b.CreatedAt as BookCreatedAt, b.UpdatedAt as BookUpdatedAt, " +
               // Author
               "BIN_TO_UUID(a.AuthorId) as AuthorIdFull, a.FullName, a.Pseudonym, " +
               "BIN_TO_UUID(a.CountryId) as AuthorCountryId, BIN_TO_UUID(a.StatusId) as AuthorStatusId, " +
               "a.Biography, a.BirthYear, a.DeathYear, a.Website, a.Email, a.PhotoUrl, " +
               "a.CreatedAt as AuthorCreatedAt, a.UpdatedAt as AuthorUpdatedAt, " +
               // Country
               "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
               "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
               // Status
               "BIN_TO_UUID(s.StatusId) as StatusIdFull, s.StatusName, " +
               "s.CreatedAt as StatusCreatedAt, s.UpdatedAt as StatusUpdatedAt, " +
               // Category
               "BIN_TO_UUID(cat.CategoryId) as CategoryIdFull, cat.CategoryName, cat.Description, " +
               "cat.CreatedAt as CategoryCreatedAt, cat.UpdatedAt as CategoryUpdatedAt, " +
               // BookStatus
               "BIN_TO_UUID(bs.BookStatusId) as BookStatusIdFull, bs.BookStatusName, " +
               "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt, " +
               // BookCopyStatus
               "BIN_TO_UUID(bcs.BookCopyStatusId) as BookCopyStatusIdFull, bcs.BookCopyStatusName, " +
               "bcs.CreatedAt as BookCopyStatusCreatedAt, bcs.UpdatedAt as BookCopyStatusUpdatedAt " +
               "FROM BookCopy bc " +
               "INNER JOIN Book b ON bc.BookId = b.BookId " +
               "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
               "INNER JOIN Country c ON a.CountryId = c.CountryId " +
               "INNER JOIN Status s ON a.StatusId = s.StatusId " +
               "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
               "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
               "INNER JOIN BookCopyStatus bcs ON bc.BookCopyStatusId = bcs.BookCopyStatusId ";
    }

    private BookCopy mapResultSetToBookCopy(ResultSet rs) throws SQLException {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(rs.getString("BookCopyId"));
        bookCopy.setBookId(rs.getString("BookId"));
        bookCopy.setBookCopyStatusId(rs.getString("BookCopyStatusId"));
        bookCopy.setNotes(rs.getString("Notes"));
        bookCopy.setCreatedAt(rs.getTimestamp("BookCopyCreatedAt").toLocalDateTime());
        bookCopy.setUpdatedAt(rs.getTimestamp("BookCopyUpdatedAt").toLocalDateTime());
        
        Book book = new Book();
        book.setBookId(rs.getString("BookIdFull"));
        book.setIsbn(rs.getString("ISBN"));
        book.setTitle(rs.getString("Title"));
        book.setAuthorId(rs.getString("AuthorId"));
        book.setCategoryId(rs.getString("CategoryId"));
        
        int publicationYear = rs.getInt("PublicationYear");
        if (!rs.wasNull()) {
            book.setPublicationYear(publicationYear);
        }
        
        book.setPublisher(rs.getString("Publisher"));
        
        int pages = rs.getInt("Pages");
        if (!rs.wasNull()) {
            book.setPages(pages);
        }
        
        book.setLanguage(rs.getString("Language"));
        book.setDescription(rs.getString("Description"));
        book.setCoverImageUrl(rs.getString("CoverImageUrl"));
        book.setBookStatusId(rs.getString("BookStatusId"));
        book.setCreatedAt(rs.getTimestamp("BookCreatedAt").toLocalDateTime());
        book.setUpdatedAt(rs.getTimestamp("BookUpdatedAt").toLocalDateTime());
        
        Author author = new Author();
        author.setAuthorId(rs.getString("AuthorIdFull"));
        author.setFullName(rs.getString("FullName"));
        author.setPseudonym(rs.getString("Pseudonym"));
        author.setCountryId(rs.getString("AuthorCountryId"));
        author.setStatusId(rs.getString("AuthorStatusId"));
        author.setBiography(rs.getString("Biography"));
        author.setBirthYear(rs.getObject("BirthYear", Integer.class));
        author.setDeathYear(rs.getObject("DeathYear", Integer.class));
        
        author.setWebsite(rs.getString("Website"));
        author.setEmail(rs.getString("Email"));
        author.setPhotoUrl(rs.getString("PhotoUrl"));
        author.setCreatedAt(rs.getTimestamp("AuthorCreatedAt").toLocalDateTime());
        author.setUpdatedAt(rs.getTimestamp("AuthorUpdatedAt").toLocalDateTime());
        
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
        
        book.setAuthor(author);
        
        Category category = new Category();
        category.setCategoryId(rs.getString("CategoryIdFull"));
        category.setCategoryName(rs.getString("CategoryName"));
        category.setDescription(rs.getString("Description"));
        category.setCreatedAt(rs.getTimestamp("CategoryCreatedAt").toLocalDateTime());
        category.setUpdatedAt(rs.getTimestamp("CategoryUpdatedAt").toLocalDateTime());
        book.setCategory(category);
        
        BookStatus bookStatus = new BookStatus();
        bookStatus.setBookStatusId(rs.getString("BookStatusIdFull"));
        bookStatus.setBookStatusName(rs.getString("BookStatusName"));
        bookStatus.setCreatedAt(rs.getTimestamp("BookStatusCreatedAt").toLocalDateTime());
        bookStatus.setUpdatedAt(rs.getTimestamp("BookStatusUpdatedAt").toLocalDateTime());
        book.setBookStatus(bookStatus);
        
        bookCopy.setBook(book);
        
        BookCopyStatus bookCopyStatus = new BookCopyStatus();
        bookCopyStatus.setBookCopyStatusId(rs.getString("BookCopyStatusIdFull"));
        bookCopyStatus.setBookCopyStatusName(rs.getString("BookCopyStatusName"));
        bookCopyStatus.setCreatedAt(rs.getTimestamp("BookCopyStatusCreatedAt").toLocalDateTime());
        bookCopyStatus.setUpdatedAt(rs.getTimestamp("BookCopyStatusUpdatedAt").toLocalDateTime());
        bookCopy.setBookCopyStatus(bookCopyStatus);
        
        return bookCopy;
    }
}