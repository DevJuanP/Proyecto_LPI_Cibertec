package repository;

import connection.DatabaseConnection;
import model.Rental;
import model.User;
import model.BookCopy;
import model.Book;
import model.Author;
import model.Category;
import model.BookStatus;
import model.BookCopyStatus;
import model.RentalStatus;
import model.Country;
import model.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalRepository implements IRentalRepository {

    @Override
    public Rental findById(String rentalId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + " WHERE r.RentalId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rentalId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRental(rs);
            }
            return null;
        }
    }

    @Override
    public List<Rental> findAll() throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + " ORDER BY r.RentalDate DESC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findByUser(String userId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE r.UserId = UUID_TO_BIN(?) " +
                     " ORDER BY r.RentalDate DESC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findByBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE r.BookCopyId = UUID_TO_BIN(?) " +
                     " ORDER BY r.RentalDate DESC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findByStatus(String rentalStatusId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE r.RentalStatusId = UUID_TO_BIN(?) " +
                     " ORDER BY r.RentalDate DESC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rentalStatusId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findActiveRentals() throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE rs.RentalStatusName = 'Active' " +
                     " ORDER BY r.DueDate ASC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findActiveRentalsByUser(String userId) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE r.UserId = UUID_TO_BIN(?) " +
                     " AND rs.RentalStatusName = 'Active' " +
                     " ORDER BY r.DueDate ASC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findOverdueRentals() throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE rs.RentalStatusName = 'Active' " +
                     " AND r.DueDate < NOW() " +
                     " ORDER BY r.DueDate ASC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public List<Rental> findDueSoon(int days) throws SQLException, ClassNotFoundException {
        String sql = buildSelectQuery() + 
                     " WHERE rs.RentalStatusName = 'Active' " +
                     " AND r.DueDate BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY) " +
                     " ORDER BY r.DueDate ASC";
        
        List<Rental> rentals = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, days);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                rentals.add(mapResultSetToRental(rs));
            }
        }
        return rentals;
    }

    @Override
    public void save(Rental rental) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Rental (RentalId, UserId, BookCopyId, RentalStatusId, " +
                     "RentalDate, DueDate, ReturnDate, RentalDays, DailyRate, TotalCost, Notes) " +
                     "VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?), UUID_TO_BIN(?), ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String rentalId = UUID.randomUUID().toString();
            rental.setRentalId(rentalId);
            
            ps.setString(1, rentalId);
            ps.setString(2, rental.getUserId());
            ps.setString(3, rental.getBookCopyId());
            ps.setString(4, rental.getRentalStatusId());
            ps.setTimestamp(5, Timestamp.valueOf(rental.getRentalDate()));
            ps.setTimestamp(6, Timestamp.valueOf(rental.getDueDate()));
            
            if (rental.getReturnDate() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(rental.getReturnDate()));
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }
            
            ps.setInt(8, rental.getRentalDays());
            ps.setBigDecimal(9, rental.getDailyRate());
            ps.setBigDecimal(10, rental.getTotalCost());
            ps.setString(11, rental.getNotes());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Rental rental) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Rental SET UserId = UUID_TO_BIN(?), BookCopyId = UUID_TO_BIN(?), " +
                     "RentalStatusId = UUID_TO_BIN(?), RentalDate = ?, DueDate = ?, ReturnDate = ?, " +
                     "RentalDays = ?, DailyRate = ?, TotalCost = ?, Notes = ? " +
                     "WHERE RentalId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rental.getUserId());
            ps.setString(2, rental.getBookCopyId());
            ps.setString(3, rental.getRentalStatusId());
            ps.setTimestamp(4, Timestamp.valueOf(rental.getRentalDate()));
            ps.setTimestamp(5, Timestamp.valueOf(rental.getDueDate()));
            
            if (rental.getReturnDate() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(rental.getReturnDate()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            
            ps.setInt(7, rental.getRentalDays());
            ps.setBigDecimal(8, rental.getDailyRate());
            ps.setBigDecimal(9, rental.getTotalCost());
            ps.setString(10, rental.getNotes());
            ps.setString(11, rental.getRentalId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(String rentalId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Rental WHERE RentalId = UUID_TO_BIN(?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, rentalId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean existsActiveRentalForBookCopy(String bookCopyId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Rental r " +
                     "INNER JOIN RentalStatus rs ON r.RentalStatusId = rs.RentalStatusId " +
                     "WHERE r.BookCopyId = UUID_TO_BIN(?) AND rs.RentalStatusName = 'Active'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookCopyId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    private String buildSelectQuery() {
        return "SELECT " +
               // Rental
               "BIN_TO_UUID(r.RentalId) as RentalId, BIN_TO_UUID(r.UserId) as UserId, " +
               "BIN_TO_UUID(r.BookCopyId) as BookCopyId, BIN_TO_UUID(r.RentalStatusId) as RentalStatusId, " +
               "r.RentalDate, r.DueDate, r.ReturnDate, r.RentalDays, r.DailyRate, r.TotalCost, r.Notes, " +
               "r.CreatedAt as RentalCreatedAt, r.UpdatedAt as RentalUpdatedAt, " +
               // User
               "BIN_TO_UUID(u.UserId) as UserIdFull, u.Email, u.Password, BIN_TO_UUID(u.StatusId) as UserStatusId, " +
               "u.CreatedAt as UserCreatedAt, u.UpdatedAt as UserUpdatedAt, " +
               // User Status
               "BIN_TO_UUID(us.StatusId) as UserStatusIdFull, us.StatusName as UserStatusName, " +
               "us.CreatedAt as UserStatusCreatedAt, us.UpdatedAt as UserStatusUpdatedAt, " +
               // BookCopy
               "BIN_TO_UUID(bc.BookCopyId) as BookCopyIdFull, BIN_TO_UUID(bc.BookId) as BookId, " +
               "BIN_TO_UUID(bc.BookCopyStatusId) as BookCopyStatusId, bc.Notes as BookCopyNotes, " +
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
               "a.Biography, a.BirthDate, a.DeathDate, a.Website, a.Email as AuthorEmail, a.PhotoUrl, " +
               "a.CreatedAt as AuthorCreatedAt, a.UpdatedAt as AuthorUpdatedAt, " +
               // Country
               "BIN_TO_UUID(c.CountryId) as CountryIdFull, c.CountryName, c.CountryCode, " +
               "c.CreatedAt as CountryCreatedAt, c.UpdatedAt as CountryUpdatedAt, " +
               // Status
               "BIN_TO_UUID(ast.StatusId) as AuthorStatusIdFull, ast.StatusName as AuthorStatusName, " +
               "ast.CreatedAt as AuthorStatusCreatedAt, ast.UpdatedAt as AuthorStatusUpdatedAt, " +
               // Category
               "BIN_TO_UUID(cat.CategoryId) as CategoryIdFull, cat.CategoryName, cat.Description, " +
               "cat.CreatedAt as CategoryCreatedAt, cat.UpdatedAt as CategoryUpdatedAt, " +
               // BookStatus
               "BIN_TO_UUID(bs.BookStatusId) as BookStatusIdFull, bs.BookStatusName, " +
               "bs.CreatedAt as BookStatusCreatedAt, bs.UpdatedAt as BookStatusUpdatedAt, " +
               // BookCopyStatus
               "BIN_TO_UUID(bcs.BookCopyStatusId) as BookCopyStatusIdFull, bcs.BookCopyStatusName, " +
               "bcs.CreatedAt as BookCopyStatusCreatedAt, bcs.UpdatedAt as BookCopyStatusUpdatedAt, " +
               // RentalStatus
               "BIN_TO_UUID(rs.RentalStatusId) as RentalStatusIdFull, rs.RentalStatusName, " +
               "rs.CreatedAt as RentalStatusCreatedAt, rs.UpdatedAt as RentalStatusUpdatedAt " +
               "FROM Rental r " +
               "INNER JOIN User u ON r.UserId = u.UserId " +
               "INNER JOIN Status us ON u.StatusId = us.StatusId " +
               "INNER JOIN BookCopy bc ON r.BookCopyId = bc.BookCopyId " +
               "INNER JOIN Book b ON bc.BookId = b.BookId " +
               "INNER JOIN Author a ON b.AuthorId = a.AuthorId " +
               "INNER JOIN Country c ON a.CountryId = c.CountryId " +
               "INNER JOIN Status ast ON a.StatusId = ast.StatusId " +
               "INNER JOIN Category cat ON b.CategoryId = cat.CategoryId " +
               "INNER JOIN BookStatus bs ON b.BookStatusId = bs.BookStatusId " +
               "INNER JOIN BookCopyStatus bcs ON bc.BookCopyStatusId = bcs.BookCopyStatusId " +
               "INNER JOIN RentalStatus rs ON r.RentalStatusId = rs.RentalStatusId ";
    }

    private Rental mapResultSetToRental(ResultSet rs) throws SQLException {
        Rental rental = new Rental();
        rental.setRentalId(rs.getString("RentalId"));
        rental.setUserId(rs.getString("UserId"));
        rental.setBookCopyId(rs.getString("BookCopyId"));
        rental.setRentalStatusId(rs.getString("RentalStatusId"));
        rental.setRentalDate(rs.getTimestamp("RentalDate").toLocalDateTime());
        rental.setDueDate(rs.getTimestamp("DueDate").toLocalDateTime());
        
        Timestamp returnDate = rs.getTimestamp("ReturnDate");
        if (returnDate != null) {
            rental.setReturnDate(returnDate.toLocalDateTime());
        }
        
        rental.setRentalDays(rs.getInt("RentalDays"));
        rental.setDailyRate(rs.getBigDecimal("DailyRate"));
        rental.setTotalCost(rs.getBigDecimal("TotalCost"));
        rental.setNotes(rs.getString("Notes"));
        rental.setCreatedAt(rs.getTimestamp("RentalCreatedAt").toLocalDateTime());
        rental.setUpdatedAt(rs.getTimestamp("RentalUpdatedAt").toLocalDateTime());
        
        User user = new User();
        user.setUserId(rs.getString("UserIdFull"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setStatusId(rs.getString("UserStatusId"));
        user.setCreatedAt(rs.getTimestamp("UserCreatedAt").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("UserUpdatedAt").toLocalDateTime());
        
        Status userStatus = new Status();
        userStatus.setStatusId(rs.getString("UserStatusIdFull"));
        userStatus.setStatusName(rs.getString("UserStatusName"));
        userStatus.setCreatedAt(rs.getTimestamp("UserStatusCreatedAt").toLocalDateTime());
        userStatus.setUpdatedAt(rs.getTimestamp("UserStatusUpdatedAt").toLocalDateTime());
        user.setStatus(userStatus);
        
        rental.setUser(user);
        
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(rs.getString("BookCopyIdFull"));
        bookCopy.setBookId(rs.getString("BookId"));
        bookCopy.setBookCopyStatusId(rs.getString("BookCopyStatusId"));
        bookCopy.setNotes(rs.getString("BookCopyNotes"));
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
        
        Date birthDate = rs.getDate("BirthDate");
        if (birthDate != null) {
            author.setBirthDate(birthDate.toLocalDate());
        }
        
        Date deathDate = rs.getDate("DeathDate");
        if (deathDate != null) {
            author.setDeathDate(deathDate.toLocalDate());
        }
        
        author.setWebsite(rs.getString("Website"));
        author.setEmail(rs.getString("AuthorEmail"));
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
        
        Status authorStatus = new Status();
        authorStatus.setStatusId(rs.getString("AuthorStatusIdFull"));
        authorStatus.setStatusName(rs.getString("AuthorStatusName"));
        authorStatus.setCreatedAt(rs.getTimestamp("AuthorStatusCreatedAt").toLocalDateTime());
        authorStatus.setUpdatedAt(rs.getTimestamp("AuthorStatusUpdatedAt").toLocalDateTime());
        author.setStatus(authorStatus);
        
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
        
        rental.setBookCopy(bookCopy);
        
        RentalStatus rentalStatus = new RentalStatus();
        rentalStatus.setRentalStatusId(rs.getString("RentalStatusIdFull"));
        rentalStatus.setRentalStatusName(rs.getString("RentalStatusName"));
        rentalStatus.setCreatedAt(rs.getTimestamp("RentalStatusCreatedAt").toLocalDateTime());
        rentalStatus.setUpdatedAt(rs.getTimestamp("RentalStatusUpdatedAt").toLocalDateTime());
        rental.setRentalStatus(rentalStatus);
        
        return rental;
    }
}