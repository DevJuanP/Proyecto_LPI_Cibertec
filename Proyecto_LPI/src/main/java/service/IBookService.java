package service;

import java.sql.SQLException;

import dto.shared.PagedResult;
import model.Book;

public interface IBookService {
    PagedResult<Book> getRegisteredBooks(int page, int pageSize, String search, 
            String authorId, String categoryId, String bookStatusId) throws SQLException, ClassNotFoundException;
    
    int getTotalBooksCount() throws SQLException, ClassNotFoundException;

    int getActiveBooksCount() throws SQLException, ClassNotFoundException;

    Book findById(String bookId) throws SQLException, ClassNotFoundException;

    void save(Book book) throws SQLException, ClassNotFoundException;

    void update(Book book) throws SQLException, ClassNotFoundException;

    void delete(String bookId) throws SQLException, ClassNotFoundException;
}