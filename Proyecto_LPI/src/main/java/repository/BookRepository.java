package repository;

import connection.DbContext;
import model.Book;
import model.Author;
import model.Category;
import model.BookStatus;
import model.Country;
import model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class BookRepository implements IBookRepository {
    private final DbContext dbContext;
    
    public BookRepository(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    @Override
    public Book findById(String bookId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.BookId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
            return null;
        }
    }

    @Override
    public Book findByIsbn(String isbn) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.ISBN = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToBook(rs);
            }
            return null;
        }
    }

    @Override
    public List<Book> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "ORDER BY b.Title ASC";
        
        List<Book> books = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String authorId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.AuthorId = UUID_TO_BIN(?) " +
                     "ORDER BY b.Title ASC";
        
        List<Book> books = new ArrayList<>();
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, authorId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    @Override
    public List<Book> findByCategory(String categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.CategoryId = UUID_TO_BIN(?) " +
                     "ORDER BY b.Title ASC";
        
        List<Book> books = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, categoryId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    @Override
    public List<Book> findByStatus(String bookStatusId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.BookStatusId = UUID_TO_BIN(?) " +
                     "ORDER BY b.Title ASC";
        
        List<Book> books = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookStatusId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    @Override
    public List<Book> searchByTitle(String title) throws SQLException, ClassNotFoundException {
        String sql = "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE b.Title LIKE ? " +
                     "ORDER BY b.Title ASC";
        
        List<Book> books = new ArrayList<>();
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
        }
        return books;
    }

    @Override
    public void save(Book book) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Book (BookId, ISBN, Title, AuthorId, CategoryId, " +
                     "PublicationYear, Publisher, Pages, Language, Description, CoverImageUrl, BookStatusId) " +
                     "VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, UUID_TO_BIN(?))";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String bookId = UUID.randomUUID().toString();
            book.setBookId(bookId);
            
            ps.setString(1, bookId);
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getAuthorId());
            ps.setString(5, book.getCategoryId());
            
            if (book.getPublicationYear() != null) {
                ps.setInt(6, book.getPublicationYear());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            
            ps.setString(7, book.getPublisher());
            
            if (book.getPages() != null) {
                ps.setInt(8, book.getPages());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            
            ps.setString(9, book.getLanguage());
            ps.setString(10, book.getDescription());
            ps.setString(11, book.getCoverImageUrl());
            ps.setString(12, book.getBookStatusId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Book book) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Book SET ISBN = ?, Title = ?, AuthorId = UUID_TO_BIN(?), " +
                     "CategoryId = UUID_TO_BIN(?), PublicationYear = ?, Publisher = ?, Pages = ?, " +
                     "Language = ?, Description = ?, CoverImageUrl = ?, BookStatusId = UUID_TO_BIN(?) " +
                     "WHERE BookId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthorId());
            ps.setString(4, book.getCategoryId());
            
            if (book.getPublicationYear() != null) {
                ps.setInt(5, book.getPublicationYear());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            
            ps.setString(6, book.getPublisher());
            
            if (book.getPages() != null) {
                ps.setInt(7, book.getPages());
            } else {
                ps.setNull(7, Types.INTEGER);
            }
            
            ps.setString(8, book.getLanguage());
            ps.setString(9, book.getDescription());
            ps.setString(10, book.getCoverImageUrl());
            ps.setString(11, book.getBookStatusId());
            ps.setString(12, book.getBookId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String bookId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Book WHERE BookId = UUID_TO_BIN(?)";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Book WHERE ISBN = ?";
        
        Connection conn = dbContext.getConnection();
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Book";

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
    public int count(String search, String authorId, String categoryId, String bookStatusId) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Book b WHERE 1=1");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (b.ISBN LIKE ? OR b.Title LIKE ?)");
        }
        if (authorId != null && !authorId.trim().isEmpty()) {
            sql.append(" AND b.AuthorId = UUID_TO_BIN(?)");
        }
        if (categoryId != null && !categoryId.trim().isEmpty()) {
            sql.append(" AND b.CategoryId = UUID_TO_BIN(?)");
        }
        if (bookStatusId != null && !bookStatusId.trim().isEmpty()) {
            sql.append(" AND b.BookStatusId = UUID_TO_BIN(?)");
        }

        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (authorId != null && !authorId.trim().isEmpty()) {
                ps.setString(paramIndex++, authorId);
            }
            if (categoryId != null && !categoryId.trim().isEmpty()) {
                ps.setString(paramIndex++, categoryId);
            }
            if (bookStatusId != null && !bookStatusId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookStatusId);
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
    public LinkedList<Book> findAllPaginated(int offset, int limit) throws SQLException, ClassNotFoundException {
        return findAllPaginated(offset, limit, null, null, null, null);
    }

    @Override
    public LinkedList<Book> findAllPaginated(int offset, int limit, String search, String authorId, String categoryId, String bookStatusId)
            throws SQLException, ClassNotFoundException {

        StringBuilder sql = new StringBuilder(
                "SELECT " +
                     "BIN_TO_UUID(b.BookId) as BookId, b.ISBN, b.Title, " +
                     "BIN_TO_UUID(b.AuthorId) as AuthorId, BIN_TO_UUID(b.CategoryId) as CategoryId, " +
                     "b.PublicationYear, b.Publisher, b.Pages, b.Language, b.Description, " +
                     "b.CoverImageUrl, BIN_TO_UUID(b.BookStatusId) as BookStatusId, " +
                     "b.CreatedAt, b.UpdatedAt, " +
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
                     "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt " +
                     "FROM Book b " +
                     "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
                     "INNER JOIN Country c ON a.CountryId = c.CountryId " +
                     "INNER JOIN Status s ON a.StatusId = s.StatusId " +
                     "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
                     "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
                     "WHERE 1=1");

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (b.ISBN LIKE ? OR b.Title LIKE ?)");
        }
        if (authorId != null && !authorId.trim().isEmpty()) {
            sql.append(" AND b.AuthorId = UUID_TO_BIN(?)");
        }
        if (categoryId != null && !categoryId.trim().isEmpty()) {
            sql.append(" AND b.CategoryId = UUID_TO_BIN(?)");
        }
        if (bookStatusId != null && !bookStatusId.trim().isEmpty()) {
            sql.append(" AND b.BookStatusId = UUID_TO_BIN(?)");
        }

        sql.append(" ORDER BY b.Title ASC LIMIT ? OFFSET ?");

        LinkedList<Book> books = new LinkedList<>();
        Connection conn = dbContext.getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim() + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            if (authorId != null && !authorId.trim().isEmpty()) {
                ps.setString(paramIndex++, authorId);
            }
            if (categoryId != null && !categoryId.trim().isEmpty()) {
                ps.setString(paramIndex++, categoryId);
            }
            if (bookStatusId != null && !bookStatusId.trim().isEmpty()) {
                ps.setString(paramIndex++, bookStatusId);
            }

            ps.setInt(paramIndex++, limit);
            ps.setInt(paramIndex, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
            }
        }
        return books;
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getString("BookId"));
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
        book.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        book.setUpdatedAt(rs.getTimestamp("UpdatedAt").toLocalDateTime());
        
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
        
        return book;
    }
}