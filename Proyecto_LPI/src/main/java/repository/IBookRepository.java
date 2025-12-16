package repository;

import java.sql.SQLException;
import java.util.List;

import model.Book;

public interface IBookRepository {
    Book findById(String bookId) throws SQLException, ClassNotFoundException;
    Book findByIsbn(String isbn) throws SQLException, ClassNotFoundException;
    List<Book> findAll() throws SQLException, ClassNotFoundException;
    List<Book> findByAuthor(String authorId) throws SQLException, ClassNotFoundException;
    List<Book> findByCategory(String categoryId) throws SQLException, ClassNotFoundException;
    List<Book> findByStatus(String bookStatusId) throws SQLException, ClassNotFoundException;
    List<Book> searchByTitle(String title) throws SQLException, ClassNotFoundException;
    void save(Book book) throws SQLException, ClassNotFoundException;
    void update(Book book) throws SQLException, ClassNotFoundException;
    void delete(String bookId) throws SQLException, ClassNotFoundException;
    boolean existsByIsbn(String isbn) throws SQLException, ClassNotFoundException;
}