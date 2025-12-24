package repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Book;
import model.BookCopy;

public interface IBookCopyRepository {
    BookCopy findById(String bookCopyId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findAll() throws SQLException, ClassNotFoundException;
    List<BookCopy> findByBook(String bookId) throws SQLException, ClassNotFoundException;
    List<BookCopy> findByStatus(String bookCopyStatusId) throws SQLException, ClassNotFoundException;
    LinkedList<Book> findAvailableBooks() throws SQLException, ClassNotFoundException;
    List<BookCopy> findAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
    BookCopy findFirstAvailableCopyByBookId(String bookId) throws SQLException, ClassNotFoundException;
    void save(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void update(BookCopy bookCopy) throws SQLException, ClassNotFoundException;
    void delete(String bookCopyId) throws SQLException, ClassNotFoundException;
    int countByBook(String bookId) throws SQLException, ClassNotFoundException;
    int countAvailableByBook(String bookId) throws SQLException, ClassNotFoundException;
    int count() throws SQLException, ClassNotFoundException;
    int count(String search, String bookId, String bookCopyStatusId) throws SQLException, ClassNotFoundException;
    LinkedList<BookCopy> findAllPaginated(int offset, int limit, String search, String bookId, String bookCopyStatusId) throws SQLException, ClassNotFoundException;
}