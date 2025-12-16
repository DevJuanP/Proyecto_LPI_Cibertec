package repository;

import java.sql.SQLException;
import java.util.List;

import model.BookCopy;

public interface IBookCopyRepository {
    BookCopy findById(String bookCopyId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findAll() throws SQLException, ClassNotFoundException;
    List<BookCopy> findByBook(String bookId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findByStatus(String bookCopyStatusId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
    void save(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void update(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void delete(String bookCopyId) throws SQLException, ClassNotFoundException;
    int countByBook(String bookId) throws SQLException, ClassNotFoundException;
    int countAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
}